package org.qfox.wectrl.service.bean.weixin;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.qfox.jestful.client.Client;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.common.weixin.WhyInvalid;
import org.qfox.wectrl.core.base.App;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by payne on 2017/3/5.
 */
@Service
public class TokenServiceBean extends GenericServiceBean<Token, Long> implements TokenService {

    private final Map<String, Token> cache = new ConcurrentHashMap<>();

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

    @Override
    public TokenApiResult getApplicationAccessToken(String appID) {
        synchronized (appID.intern()) {
            Token token = cache.get(appID);
            if (token == null || token.isExpired()) {
                Criteria criteria = tokenDAO.createCriteria();
                criteria.add(Restrictions.eq("application.appID", appID));
                criteria.add(Restrictions.eq("deleted", false));
                criteria.add(Restrictions.eq("invalid", false));
                criteria.addOrder(Order.desc("timeExpired"));
                criteria.setFirstResult(0);
                criteria.setMaxResults(1);
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                token = (Token) criteria.uniqueResult();
                if (token == null || token.isExpired()) {
                    if (token != null) {
                        token.setInvalid(true);
                        token.setDateInvalid(new Date());
                        token.setWhyInvalid(WhyInvalid.EXPIRED);
                        tokenServiceBean.update(token);
                    }
                    Application application = applicationServiceBean.getApplicationByAppID(appID);
                    String appSecret = application.getAppSecret();
                    TokenApiResult result = WeixinCgiBinAPI.INSTANCE.token(TokenType.client_credential, appID, appSecret);
                    if (result.isSuccess()) {
                        token = new Token();
                        token.setApplication(new App(application));
                        token.setValue(result.getAccess_token());
                        token.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
                        tokenServiceBean.save(token);
                    } else {
                        return result;
                    }
                }
                cache.put(appID, token);
            }
            TokenApiResult result = new TokenApiResult();
            result.setAccess_token(token.getValue());
            result.setExpires_in(token.getSecondsExpired());
            return result;
        }
    }

    @Override
    public TokenApiResult newApplicationAccessToken(String appID) {
        synchronized (appID.intern()) {
            Application application = applicationServiceBean.getApplicationByAppID(appID);
            String appSecret = application.getAppSecret();
            TokenApiResult result = WeixinCgiBinAPI.INSTANCE.token(TokenType.client_credential, appID, appSecret);
            if (result.isSuccess()) {
                Criteria criteria = tokenDAO.createCriteria();
                criteria.add(Restrictions.eq("application.appID", appID));
                criteria.add(Restrictions.eq("deleted", false));
                criteria.add(Restrictions.eq("invalid", false));
                criteria.addOrder(Order.desc("timeExpired"));
                criteria.setFirstResult(0);
                criteria.setMaxResults(1);
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                Token token = (Token) criteria.uniqueResult();
                if (token != null) {
                    token.setInvalid(true);
                    token.setDateInvalid(new Date());
                    token.setWhyInvalid(WhyInvalid.RESET);
                    tokenServiceBean.update(token);
                }

                token = new Token();
                token.setApplication(new App(application));
                token.setValue(result.getAccess_token());
                token.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
                tokenServiceBean.save(token);
                cache.put(appID, token);
            }
            return result;
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
}
