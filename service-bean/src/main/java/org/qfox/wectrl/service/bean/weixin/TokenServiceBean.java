package org.qfox.wectrl.service.bean.weixin;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.qfox.jestful.client.Message;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.Token;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.weixin.TokenDAO;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.qfox.wectrl.service.weixin.TokenService;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenType;
import org.qfox.wectrl.service.weixin.cgi_bin.WeixinCgiBinAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by payne on 2017/3/5.
 */
@Service
public class TokenServiceBean extends GenericServiceBean<Token, Long> implements TokenService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<String, TokenHolder> cache = new ConcurrentHashMap<>();

    @Resource
    private TokenDAO tokenDAO;

    @Resource
    private TokenService tokenServiceBean;

    @Resource
    private ApplicationService applicationServiceBean;

    @Override
    protected GenericDAO<Token, Long> getEntityDAO() {
        return tokenDAO;
    }

    private static final class TokenHolder {
        private final Token token;
        private final long timeExpired;

        public TokenHolder(Token token) {
            this.token = token;
            // 一分钟过期 因为刷新后的Access Token 仍有5分钟有效期 主要是考虑 集群环境中另外的服务器刷新了Access Token 但当前机器不知道 所以一分钟就需要到数据库拿最新的
            // 就即便我现在拿到的可用的Access Token 下一秒就被刷新了 我拿到的也仍有5分钟有效期 我用2分钟做缓存 留3分钟给客户端使用
            this.timeExpired = System.currentTimeMillis() + 2L * 60L * 1000L;
        }

        public final boolean isExpired() {
            return System.currentTimeMillis() > timeExpired;
        }
    }

    private Token getDatabaseAccessToken(String appID) {
        Criteria criteria = tokenDAO.createCriteria();
        criteria.add(Restrictions.eq("application.appID", appID));
        criteria.add(Restrictions.eq("deleted", false));
        criteria.add(Restrictions.eq("invalid", false));
        criteria.addOrder(Order.desc("timeExpired"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(1);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Token) criteria.uniqueResult();
    }

    // 必须在向数据库获取到刷新权限才能调用该方法!!!!!!!!!
    private TokenApiResult getWeixinAccessToken(String appID) {
        try {
            Application application = applicationServiceBean.getApplicationByAppID(appID);
            String appSecret = application.getAppSecret();
            Message<TokenApiResult> message = WeixinCgiBinAPI.INSTANCE.token(TokenType.client_credential, appID, appSecret);
            if (!message.isSuccess()) {
                TokenApiResult result = new TokenApiResult();
                result.setErrcode(500);
                result.setErrmsg("未知错误");
                return result;
            } else {
                TokenApiResult result = message.getEntity();
                Token token = new Token();
                token.setValue(result.getAccess_token());
                token.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
                token.setInvalid(false);
                token.setWhyInvalid(null);
                token.setDateInvalid(null);
                tokenServiceBean.save(token);

                TokenHolder holder = new TokenHolder(token);
                cache.put(appID, holder);
                return result;
            }
        } catch (Exception e) {
            logger.error("getting access token from weixin fail", e);
            TokenApiResult result = new TokenApiResult();
            result.setErrcode(500);
            result.setErrmsg("未知错误");
            return result;
        } finally {
            applicationServiceBean.endRefreshing(appID);
        }
    }

    @Override
    public TokenApiResult getApplicationAccessToken(String appID) {
        TokenHolder holder = cache.get(appID);
        // 0. 如果没有缓存 或者 已经过期了
        if (holder == null || holder.isExpired()) {
            // 1. 先从数据库获取最新有效的Access Token
            Token token = getDatabaseAccessToken(appID);
            // 2. 如果数据库中没有有效的Access Token 或者已经实际上过期
            if (token == null || token.isExpiredActually()) {
                // 3. 获取刷新Access Token的权限 利用数据库的事务性 保证同时只能有一个线程再刷新 Access Token
                boolean permission = applicationServiceBean.startRefreshing(appID);
                // 4. 如果获取到了权限
                if (permission) {
                    return getWeixinAccessToken(appID);
                } else {
                    try {
                        // 休眠1秒钟再尝试获取
                        Thread.sleep(1L * 1000L);
                        return getApplicationAccessToken(appID);
                    } catch (InterruptedException e) {
                        logger.error("getting access token fail", e);
                        TokenApiResult result = new TokenApiResult();
                        result.setErrcode(500);
                        result.setErrmsg("未知错误");
                        return result;
                    }
                }
            }
            // 5. 如果只是是逻辑上过期 则获取到刷新权限的线程负责到微信请求刷新Access Token并缓存 获取不到更新权限的线程立刻返回当前的 Access Token
            else if (token.isExpiredLogically()) {
                boolean permission = applicationServiceBean.startRefreshing(appID);
                if (permission) {
                    return getWeixinAccessToken(appID);
                } else {
                    TokenApiResult result = new TokenApiResult();
                    result.setErrcode(0);
                    result.setErrmsg("OK");
                    result.setAccess_token(token.getValue());
                    result.setExpires_in(token.getValidSeconds());
                    return result;
                }
            }
            // 6. 如果数据库中的Access Token 就是可用的 但是内存中没有 缓存之 这种情况一般是在集群环境中或者服务器重启了
            else {
                holder = new TokenHolder(token);
                cache.put(appID, holder);

                TokenApiResult result = new TokenApiResult();
                result.setErrcode(0);
                result.setErrmsg("OK");
                result.setAccess_token(token.getValue());
                result.setExpires_in(token.getValidSeconds());
                return result;
            }
        }
        // 7. 如果缓存中就是可用的
        else {
            TokenApiResult result = new TokenApiResult();
            result.setErrcode(0);
            result.setErrmsg("OK");
            result.setAccess_token(holder.token.getValue());
            result.setExpires_in(holder.token.getValidSeconds());
            return result;
        }
    }

    @Override
    public TokenApiResult newApplicationAccessToken(String appID) {
        // 向数据库申请刷新权限
        boolean permission = applicationServiceBean.startRefreshing(appID);
        // 获取到到权限的负责去刷新
        if (permission) {
            return getWeixinAccessToken(appID);
        }
        // 没有获取到的等待刷新线程结束
        else {
            try {
                // 休眠1秒钟再尝试获取
                Thread.sleep(1L * 1000L);
                return newApplicationAccessToken(appID);
            } catch (InterruptedException e) {
                logger.error("getting access token fail", e);
                TokenApiResult result = new TokenApiResult();
                result.setErrcode(500);
                result.setErrmsg("未知错误");
                return result;
            }
        }
    }

    @Override
    public Page<Token> getPagedApplicationTokens(String appID, int pagination, int capacity) {
        Page<Token> page = new Page<>(pagination, capacity);

        Criteria criteria = tokenDAO.createCriteria();
        criteria.add(Restrictions.eq("application.appID", appID));
        criteria.add(Restrictions.eq("deleted", false));
        criteria.setProjection(Projections.countDistinct("id"));
        Object total = criteria.uniqueResult();
        page.setTotal(total == null ? 0 : Integer.valueOf(total.toString()));

        if (page.getTotal() > 0 && page.getTotal() > pagination * capacity) {
            criteria.setProjection(null);
            criteria.setFirstResult(pagination * capacity);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(capacity);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Token> tokens = criteria.list();
            page.setEntities(tokens);
        }

        return page;
    }

    @Override
    public Token getTokenByValue(String value) {
        Criteria criteria = tokenDAO.createCriteria();
        criteria.add(Restrictions.eq("value", value));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Token) criteria.uniqueResult();
    }

}
