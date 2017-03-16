package org.qfox.wectrl.service.bean.weixin;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.qfox.jestful.client.Message;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.common.weixin.TicketType;
import org.qfox.wectrl.common.weixin.WhyInvalid;
import org.qfox.wectrl.core.weixin.Ticket;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.weixin.TicketDAO;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.qfox.wectrl.service.weixin.TicketService;
import org.qfox.wectrl.service.weixin.cgi_bin.TicketApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.WeixinCgiBinAPI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by payne on 2017/3/5.
 */
@Service
public class TicketServiceBean extends GenericServiceBean<Ticket, Long> implements TicketService {

    @Resource
    private TicketDAO ticketDAO;

    private final Map<String, Ticket> JSAPICache = new ConcurrentHashMap<>(); // {accessToken: jsapi_ticket}
    private final Map<String, Ticket> WXCardCache = new ConcurrentHashMap<>(); // {accessToken: wxcard_ticket}

    @Override
    protected GenericDAO<Ticket, Long> getEntityDAO() {
        return ticketDAO;
    }

    @Resource
    private TicketService ticketServiceBean;

    @Override
    public TicketApiResult getApplicationJSAPITicket(String accessToken) {
        synchronized (accessToken.intern()) {
            Ticket ticket = JSAPICache.get(accessToken);
            if (ticket == null || ticket.isExpired()) {
                Criteria criteria = ticketDAO.createCriteria();
                criteria.add(Restrictions.eq("accessToken", accessToken));
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
                    Message<TicketApiResult> message = WeixinCgiBinAPI.WECHAT.ticket(accessToken, org.qfox.wectrl.service.weixin.cgi_bin.TicketType.jsapi);
                    TicketApiResult result = null;
                    if (message != null && message.isSuccess()) {
                        result = message.getEntity();
                    } else {
                        result = new TicketApiResult();
                        result.setErrcode(500);
                        result.setErrmsg("未知错误");
                    }
                    if (result.isSuccess()) {
                        ticket = new Ticket();
                        ticket.setAccessToken(accessToken);
                        ticket.setValue(result.getTicket());
                        ticket.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
                        ticket.setType(TicketType.JSAPI);
                        ticketServiceBean.save(ticket);
                    } else {
                        return result;
                    }
                }
                JSAPICache.put(accessToken, ticket);
            }
            TicketApiResult result = new TicketApiResult();
            result.setTicket(ticket.getValue());
            result.setExpires_in(ticket.getSecondsExpired());
            return result;
        }
    }

    @Override
    public TicketApiResult newApplicationJSAPITicket(String accessToken) {
        synchronized (accessToken.intern()) {
            Message<TicketApiResult> message = WeixinCgiBinAPI.WECHAT.ticket(accessToken, org.qfox.wectrl.service.weixin.cgi_bin.TicketType.jsapi);
            TicketApiResult result = null;
            if (message != null && message.isSuccess()) {
                result = message.getEntity();
            } else {
                result = new TicketApiResult();
                result.setErrcode(500);
                result.setErrmsg("未知错误");
            }
            if (result.isSuccess()) {
                Criteria criteria = ticketDAO.createCriteria();
                criteria.add(Restrictions.eq("accessToken", accessToken));
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
                ticket.setAccessToken(accessToken);
                ticket.setValue(result.getTicket());
                ticket.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
                ticket.setType(TicketType.JSAPI);
                ticketServiceBean.save(ticket);
                JSAPICache.put(accessToken, ticket);
            }
            return result;
        }
    }

    @Override
    public TicketApiResult getApplicationWXCardTicket(String accessToken) {
        synchronized (accessToken.intern()) {
            Ticket ticket = WXCardCache.get(accessToken);
            if (ticket == null || ticket.isExpired()) {
                Criteria criteria = ticketDAO.createCriteria();
                criteria.add(Restrictions.eq("accessToken", accessToken));
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
                    Message<TicketApiResult> message = WeixinCgiBinAPI.WECHAT.ticket(accessToken, org.qfox.wectrl.service.weixin.cgi_bin.TicketType.wx_card);
                    TicketApiResult result = null;
                    if (message != null && message.isSuccess()) {
                        result = message.getEntity();
                    } else {
                        result = new TicketApiResult();
                        result.setErrcode(500);
                        result.setErrmsg("未知错误");
                    }
                    if (result.isSuccess()) {
                        ticket = new Ticket();
                        ticket.setAccessToken(accessToken);
                        ticket.setValue(result.getTicket());
                        ticket.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
                        ticket.setType(TicketType.WX_CARD);
                        ticketServiceBean.save(ticket);
                    } else {
                        return result;
                    }
                }
                WXCardCache.put(accessToken, ticket);
            }
            TicketApiResult result = new TicketApiResult();
            result.setTicket(ticket.getValue());
            result.setExpires_in(ticket.getSecondsExpired());
            return result;
        }
    }

    @Override
    public TicketApiResult newApplicationWXCardTicket(String accessToken) {
        synchronized (accessToken.intern()) {
            Message<TicketApiResult> message = WeixinCgiBinAPI.WECHAT.ticket(accessToken, org.qfox.wectrl.service.weixin.cgi_bin.TicketType.wx_card);
            TicketApiResult result = null;
            if (message != null && message.isSuccess()) {
                result = message.getEntity();
            } else {
                result = new TicketApiResult();
                result.setErrcode(500);
                result.setErrmsg("未知错误");
            }
            if (result.isSuccess()) {
                Criteria criteria = ticketDAO.createCriteria();
                criteria.add(Restrictions.eq("accessToken", accessToken));
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
                ticket.setAccessToken(accessToken);
                ticket.setValue(result.getTicket());
                ticket.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
                ticket.setType(TicketType.WX_CARD);
                ticketServiceBean.save(ticket);
                WXCardCache.put(accessToken, ticket);
            }
            return result;
        }
    }

    @Override
    public Page<Ticket> getPagedApplicationTickets(String appID, TicketType type, int pagination, int capacity) {
        return ticketDAO.getPagedApplicationTickets(appID, type, pagination, capacity);
    }

}
