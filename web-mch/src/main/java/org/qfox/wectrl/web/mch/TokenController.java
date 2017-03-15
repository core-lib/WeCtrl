package org.qfox.wectrl.web.mch;

import org.qfox.jestful.client.Message;
import org.qfox.jestful.core.annotation.*;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.Token;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.weixin.TokenService;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenType;
import org.qfox.wectrl.service.weixin.cgi_bin.WeixinCgiBinAPI;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by payne on 2017/3/5.
 */
@Jestful("/applications/{appID:\\w+}/tokens")
@Controller
public class TokenController {

    @Resource
    private ApplicationService applicationServiceBean;

    @Resource
    private TokenService tokenServiceBean;

    @GET("/")
    public String index(@Path("appID") String appID,
                        @Query("pagination") int pagination,
                        @Query("capacity") int capacity,
                        HttpServletRequest request) {
        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Application app = applicationServiceBean.getApplicationByAppID(appID);
        request.setAttribute("app", app);

        Page<Token> page = tokenServiceBean.getPagedApplicationTokens(appID, pagination, capacity);
        request.setAttribute("page", page);

        return "forward:/view/weixin/token/index.jsp";
    }

    @POST("/")
    public JsonResult refresh(@Path("appID") String appID) {
        Application app = applicationServiceBean.getApplicationByAppID(appID);
        Message<TokenApiResult> message = WeixinCgiBinAPI.WXCTRL.token(TokenType.client_credential, app.getAppID(), app.getSecret());
        if (message == null || !message.isSuccess()) {
            return JsonResult.FAIL;
        }
        return new JsonResult(message.getEntity());
    }

}
