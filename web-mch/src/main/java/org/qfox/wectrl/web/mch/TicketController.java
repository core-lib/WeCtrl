package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.*;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.common.weixin.TicketType;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.Ticket;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.weixin.TicketService;
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
        switch (type) {
            case JSAPI:
                return new JsonResult(ticketServiceBean.newApplicationJSAPITicket(appID));
            case WX_CARD:
                return new JsonResult(ticketServiceBean.newApplicationWXCardTicket(appID));
            default:
                return new JsonResult(false, "unknown ticket type : " + type, type);
        }
    }

}
