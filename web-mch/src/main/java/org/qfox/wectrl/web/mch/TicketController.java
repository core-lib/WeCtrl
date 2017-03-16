package org.qfox.wectrl.web.mch;

import org.qfox.jestful.client.Message;
import org.qfox.jestful.core.annotation.*;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.common.weixin.TicketType;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.Ticket;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.weixin.TicketService;
import org.qfox.wectrl.service.weixin.cgi_bin.TicketApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenType;
import org.qfox.wectrl.service.weixin.cgi_bin.WxCtrlCgiBinAPI;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by payne on 2017/3/5.
 */
@Jestful("/applications/{appID:\\w+}/tickets")
@Controller
public class TicketController {

    @Resource
    private ApplicationService applicationServiceBean;

    @Resource
    private TicketService ticketServiceBean;

    @GET("/")
    public String index(@Path("appID") String appID,
                        @Query("type") TicketType type,
                        @Query("pagination") int pagination,
                        @Query("capacity") int capacity,
                        HttpServletRequest request) {
        type = type == null ? TicketType.JSAPI : type;
        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Application app = applicationServiceBean.getApplicationByAppID(appID);
        request.setAttribute("app", app);

        Page<Ticket> page = ticketServiceBean.getPagedApplicationTickets(appID, type, pagination, capacity);
        request.setAttribute("page", page);

        return "forward:/view/weixin/ticket/index.jsp";
    }

    @POST("/")
    public JsonResult refresh(@Path("appID") String appID, @Body("type") TicketType type) {
        if (type == null) {
            return new JsonResult(false, "type is required", null);
        }
        Application app = applicationServiceBean.getApplicationByAppID(appID);
        Message<TokenApiResult> token = WxCtrlCgiBinAPI.INSTANCE.getToken(TokenType.client_credential, app.getAppID(), app.getSecret());
        if (token == null || !token.isSuccess()) {
            return JsonResult.FAIL;
        }
        String accessToken = token.getEntity().getAccess_token();
        switch (type) {
            case JSAPI:
                Message<TicketApiResult> jsapi = WxCtrlCgiBinAPI.INSTANCE.newTicket(accessToken, org.qfox.wectrl.service.weixin.cgi_bin.TicketType.jsapi);
                if (jsapi == null || !jsapi.isSuccess()) {
                    return JsonResult.FAIL;
                }
                return new JsonResult(jsapi.getEntity());
            case WX_CARD:
                Message<TicketApiResult> wxcard = WxCtrlCgiBinAPI.INSTANCE.newTicket(accessToken, org.qfox.wectrl.service.weixin.cgi_bin.TicketType.wx_card);
                if (wxcard == null || !wxcard.isSuccess()) {
                    return JsonResult.FAIL;
                }
                return new JsonResult(wxcard.getEntity());
            default:
                return new JsonResult(false, "unknown ticket type : " + type, type);
        }
    }

}
