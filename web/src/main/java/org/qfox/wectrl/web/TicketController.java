package org.qfox.wectrl.web;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.POST;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.wectrl.common.weixin.WhyInvalid;
import org.qfox.wectrl.core.weixin.Token;
import org.qfox.wectrl.service.weixin.TicketService;
import org.qfox.wectrl.service.weixin.TokenService;
import org.qfox.wectrl.service.weixin.cgi_bin.TicketApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.TicketType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by payne on 2017/3/16.
 */
@Jestful("/")
@Controller
public class TicketController {

    @Resource
    private TokenService tokenServiceBean;

    @Resource
    private TicketService ticketServiceBean;

    @GET(value = "/cgi-bin/ticket/getticket", produces = "application/json; charset=UTF-8")
    public TicketApiResult get(@Query("access_token") String accessToken, @Query("type") TicketType type) {
        if (StringUtils.isEmpty(accessToken)) {
            TicketApiResult result = new TicketApiResult();
            result.setErrcode(41001);
            result.setErrmsg("缺少access_token参数");
            return result;
        }
        Token token = tokenServiceBean.getTokenByValue(accessToken);
        if (token == null || token.isExpiredActually()) {
            TicketApiResult result = new TicketApiResult();
            result.setErrcode(40014);
            result.setErrmsg("invalid access_token");
            return result;
        }
        // 重设后超过五分钟不可用
        if (token.isInvalid() && token.getWhyInvalid() == WhyInvalid.REFRESHED && (System.currentTimeMillis() - token.getDateInvalid().getTime() > 5L * 60L * 1000L)) {
            TicketApiResult result = new TicketApiResult();
            result.setErrcode(40014);
            result.setErrmsg("invalid access_token");
            return result;
        }
        if (type == null) {
            TicketApiResult result = new TicketApiResult();
            result.setErrcode(400);
            result.setErrmsg("未指定 ticket type");
            return result;
        }

        switch (type) {
            case jsapi:
                return ticketServiceBean.getApplicationJSAPITicket(token.getApplication().getAppID());
            case wx_card:
                return ticketServiceBean.getApplicationWXCardTicket(token.getApplication().getAppID());
            default:
                TicketApiResult result = new TicketApiResult();
                result.setErrcode(400);
                result.setErrmsg("未知 ticket type");
                return result;
        }
    }

    @POST(value = "/cgi-bin/ticket/getticket", produces = "application/json; charset=UTF-8")
    public TicketApiResult post(@Query("access_token") String accessToken, @Query("type") TicketType type) {
        if (StringUtils.isEmpty(accessToken)) {
            TicketApiResult result = new TicketApiResult();
            result.setErrcode(41001);
            result.setErrmsg("缺少access_token参数");
            return result;
        }
        Token token = tokenServiceBean.getTokenByValue(accessToken);
        if (token == null || token.isExpiredActually()) {
            TicketApiResult result = new TicketApiResult();
            result.setErrcode(40014);
            result.setErrmsg("invalid access_token");
            return result;
        }
        // 重设后超过五分钟不可用
        if (token.isInvalid() && token.getWhyInvalid() == WhyInvalid.REFRESHED && (System.currentTimeMillis() - token.getDateInvalid().getTime() > 5L * 60L * 1000L)) {
            TicketApiResult result = new TicketApiResult();
            result.setErrcode(40014);
            result.setErrmsg("invalid access_token");
            return result;
        }
        if (type == null) {
            TicketApiResult result = new TicketApiResult();
            result.setErrcode(400);
            result.setErrmsg("未指定 ticket type");
            return result;
        }

        switch (type) {
            case jsapi:
                return ticketServiceBean.newApplicationJSAPITicket(token.getApplication().getAppID());
            case wx_card:
                return ticketServiceBean.newApplicationWXCardTicket(token.getApplication().getAppID());
            default:
                TicketApiResult result = new TicketApiResult();
                result.setErrcode(400);
                result.setErrmsg("未知 ticket type");
                return result;
        }
    }

}
