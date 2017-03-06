package org.qfox.wectrl.service.bean.weixin;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.qfox.jestful.client.Client;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.common.weixin.TicketType;
import org.qfox.wectrl.common.weixin.WhyInvalid;
import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.Ticket;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.weixin.TicketDAO;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.qfox.wectrl.service.weixin.TicketService;
import org.qfox.wectrl.service.weixin.TokenService;
import org.qfox.wectrl.service.weixin.cgi_bin.TicketApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenApiResult;
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
public class TicketServiceBean extends GenericServiceBean<Ticket, Long> implements TicketService {

    @Resource
    private WeixinCgiBinAPI weixinCgiBinAPI;

    @Resource
    private TicketDAO ticketDAO;

    private final Map<String, Ticket> JSAPICache = new ConcurrentHashMap<>();
    private final Map<String, Ticket> WXCardCache = new ConcurrentHashMap<>();

    @Override
    protected GenericDAO<Ticket, Long> getEntityDAO() {
        return ticketDAO;
    }

    @Resource
    private TicketService ticketServiceBean;

    @Resource
    private TokenService tokenServiceBean;

    @Resource
    private ApplicationService applicationServiceBean;

    @Override
    public TicketApiResult getApplicationJSAPITicket(String appID) {
        synchronized (appID.intern()) {
            Ticket ticket = JSAPICache.get(appID);
            if (ticket == null || ticket.isExpired()) {
                Criteria criteria = ticketDAO.createCriteria();
                criteria.add(Restrictions.eq("application.appID", appID));
                criteria.add(Restrictions.eq("type", TicketType.JSAPI));
                criteria.add(Restrictions.eq("deleted", false));
                criteria.add(Restrictions.eq("invalid", false));
                criteria.addOrder(Order.desc("timeExpired"));
                criteria.setFirstResult(0);
                criteria.setMaxResults(1);
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                ticket = (Ticket) criteria.uniqueResult();
                if (ticket == null || ticket.isExpired()) {
                    if (ticket != null) {
                        ticket.setInvalid(true);
                        ticket.setDateInvalid(new Date());
                        ticket.setWhyInvalid(WhyInvalid.EXPIRED);
                        ticketServiceBean.update(ticket);
                    }
                    Application application = applicationServiceBean.getApplicationByAppID(appID);
                    TokenApiResult token = tokenServiceBean.getApplicationAccessToken(appID);
                    if (!token.isSuccess()) {
                        TicketApiResult result = new TicketApiResult();
                        result.setErrcode(token.getErrcode());
                        result.setErrmsg(token.getErrmsg());
                        return result;
                    }
                    TicketApiResult result = weixinCgiBinAPI.ticket(token.getAccess_token(), org.qfox.wectrl.service.weixin.cgi_bin.TicketType.jsapi);
                    if (result.isSuccess()) {
                        ticket = new Ticket();
                        ticket.setApplication(new App(application));
                        ticket.setValue(result.getTicket());
                        ticket.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
                        ticket.setType(TicketType.JSAPI);
                        ticketServiceBean.save(ticket);
                    } else {
                        return result;
                    }
                }
                JSAPICache.put(appID, ticket);
            }
            TicketApiResult result = new TicketApiResult();
            result.setTicket(ticket.getValue());
            result.setExpires_in(ticket.getSecondsExpired());
            return result;
        }
    }

    @Override
    public TicketApiResult newApplicationJSAPITicket(String appID) {
        synchronized (appID.intern()) {
            Application application = applicationServiceBean.getApplicationByAppID(appID);
            TokenApiResult token = tokenServiceBean.getApplicationAccessToken(appID);
            if (!token.isSuccess()) {
                TicketApiResult result = new TicketApiResult();
                result.setErrcode(token.getErrcode());
                result.setErrmsg(token.getErrmsg());
                return result;
            }
            TicketApiResult result = weixinCgiBinAPI.ticket(token.getAccess_token(), org.qfox.wectrl.service.weixin.cgi_bin.TicketType.jsapi);
            if (result.isSuccess()) {
                Criteria criteria = ticketDAO.createCriteria();
                criteria.add(Restrictions.eq("application.appID", appID));
                criteria.add(Restrictions.eq("type", TicketType.JSAPI));
                criteria.add(Restrictions.eq("deleted", false));
                criteria.add(Restrictions.eq("invalid", false));
                criteria.addOrder(Order.desc("timeExpired"));
                criteria.setFirstResult(0);
                criteria.setMaxResults(1);
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                Ticket ticket = (Ticket) criteria.uniqueResult();
                if (ticket != null) {
                    ticket.setInvalid(true);
                    ticket.setDateInvalid(new Date());
                    ticket.setWhyInvalid(WhyInvalid.RESET);
                    ticketServiceBean.update(ticket);
                }

                ticket = new Ticket();
                ticket.setApplication(new App(application));
                ticket.setValue(result.getTicket());
                ticket.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
                ticket.setType(TicketType.JSAPI);
                ticketServiceBean.save(ticket);
                JSAPICache.put(appID, ticket);
            }
            return result;
        }
    }

    @Override
    public TicketApiResult getApplicationWXCardTicket(String appID) {
        synchronized (appID.intern()) {
            Ticket ticket = WXCardCache.get(appID);
            if (ticket == null || ticket.isExpired()) {
                Criteria criteria = ticketDAO.createCriteria();
                criteria.add(Restrictions.eq("application.appID", appID));
                criteria.add(Restrictions.eq("type", TicketType.WX_CARD));
                criteria.add(Restrictions.eq("deleted", false));
                criteria.add(Restrictions.eq("invalid", false));
                criteria.addOrder(Order.desc("timeExpired"));
                criteria.setFirstResult(0);
                criteria.setMaxResults(1);
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                ticket = (Ticket) criteria.uniqueResult();
                if (ticket == null || ticket.isExpired()) {
                    if (ticket != null) {
                        ticket.setInvalid(true);
                        ticket.setDateInvalid(new Date());
                        ticket.setWhyInvalid(WhyInvalid.EXPIRED);
                        ticketServiceBean.update(ticket);
                    }
                    Application application = applicationServiceBean.getApplicationByAppID(appID);
                    TokenApiResult token = tokenServiceBean.getApplicationAccessToken(appID);
                    if (!token.isSuccess()) {
                        TicketApiResult result = new TicketApiResult();
                        result.setErrcode(token.getErrcode());
                        result.setErrmsg(token.getErrmsg());
                        return result;
                    }
                    TicketApiResult result = weixinCgiBinAPI.ticket(token.getAccess_token(), org.qfox.wectrl.service.weixin.cgi_bin.TicketType.jsapi);
                    if (result.isSuccess()) {
                        ticket = new Ticket();
                        ticket.setApplication(new App(application));
                        ticket.setValue(result.getTicket());
                        ticket.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
                        ticket.setType(TicketType.WX_CARD);
                        ticketServiceBean.save(ticket);
                    } else {
                        return result;
                    }
                }
                WXCardCache.put(appID, ticket);
            }
            TicketApiResult result = new TicketApiResult();
            result.setTicket(ticket.getValue());
            result.setExpires_in(ticket.getSecondsExpired());
            return result;
        }
    }

    @Override
    public TicketApiResult newApplicationWXCardTicket(String appID) {
        synchronized (appID.intern()) {
            Application application = applicationServiceBean.getApplicationByAppID(appID);
            TokenApiResult token = tokenServiceBean.getApplicationAccessToken(appID);
            if (!token.isSuccess()) {
                TicketApiResult result = new TicketApiResult();
                result.setErrcode(token.getErrcode());
                result.setErrmsg(token.getErrmsg());
                return result;
            }
            TicketApiResult result = weixinCgiBinAPI.ticket(token.getAccess_token(), org.qfox.wectrl.service.weixin.cgi_bin.TicketType.jsapi);
            if (result.isSuccess()) {
                Criteria criteria = ticketDAO.createCriteria();
                criteria.add(Restrictions.eq("application.appID", appID));
                criteria.add(Restrictions.eq("type", TicketType.WX_CARD));
                criteria.add(Restrictions.eq("deleted", false));
                criteria.add(Restrictions.eq("invalid", false));
                criteria.addOrder(Order.desc("timeExpired"));
                criteria.setFirstResult(0);
                criteria.setMaxResults(1);
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                Ticket ticket = (Ticket) criteria.uniqueResult();
                if (ticket != null) {
                    ticket.setInvalid(true);
                    ticket.setDateInvalid(new Date());
                    ticket.setWhyInvalid(WhyInvalid.RESET);
                    ticketServiceBean.update(ticket);
                }

                ticket = new Ticket();
                ticket.setApplication(new App(application));
                ticket.setValue(result.getTicket());
                ticket.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
                ticket.setType(TicketType.WX_CARD);
                ticketServiceBean.save(ticket);
                WXCardCache.put(appID, ticket);
            }
            return result;
        }
    }

    @Override
    public Page<Ticket> getPagedApplicationTickets(String appID, TicketType type, int pagination, int capacity) {
        Page<Ticket> page = new Page<>(pagination, capacity);

        Criteria criteria = ticketDAO.createCriteria();
        criteria.add(Restrictions.eq("application.appID", appID));
        criteria.add(Restrictions.eq("type", type));
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
            List<Ticket> tickets = criteria.list();
            page.setEntities(tickets);
        }

        return page;
    }

}
