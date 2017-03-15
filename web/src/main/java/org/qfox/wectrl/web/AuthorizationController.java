package org.qfox.wectrl.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.qfox.jestful.client.Message;
import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.State;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.service.weixin.StateService;
import org.qfox.wectrl.service.weixin.UserService;
import org.qfox.wectrl.service.weixin.sns.SnsAccessTokenApiResult;
import org.qfox.wectrl.service.weixin.sns.SnsGrantType;
import org.qfox.wectrl.service.weixin.sns.WeixinSnsAPI;
import org.qfox.wectrl.web.utils.HTTPKit;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by yangchangpei on 17/3/13.
 */
@Jestful("/")
@Controller
public class AuthorizationController {

    @Resource
    private StateService stateServiceBean;

    @Resource
    private UserService userServiceBean;

    @GET("/connect/oauth2/authorize")
    public String authorize(@Query("appid") String appID,
                            @Query("redirect_uri") String redirectURI,
                            @Query("response_type") String responseType,
                            @Query("scope") String scope,
                            @Query("state") String state,
                            HttpServletRequest request) throws JsonProcessingException, UnsupportedEncodingException {

        State s = new State();
        s.setRedirectURI(redirectURI);
        s.setValue(state);
        s.setResponseType(responseType);
        stateServiceBean.save(s);

        String scheme = HTTPKit.getClosestScheme(request, "http");
        String domain = request.getServerName();
        String redirectURL = scheme + "//" + domain + "/authorization";

        String result = "https://open.weixin.qq.com/connect/oauth2/authorize"
                + "?appid=" + appID
                + "&redirect_uri=" + URLEncoder.encode(redirectURL, "UTF-8")
                + "&response_type=" + responseType
                + "&scope=" + scope
                + "&state=" + s.getId()
                + "#wechat_redirect";

        return "redirect:" + result;
    }

    @GET("/authorization")
    public String authorize(@Query("code") String code,
                            @Query("state") Long stateId,
                            Application app) throws UnsupportedEncodingException {

        String appID = app.getAppID();
        String appSecret = app.getAppSecret();

        Message<SnsAccessTokenApiResult> message = WeixinSnsAPI.INSTANCE.accessToken(appID, appSecret, code, SnsGrantType.authorization_code);
        // 如果用户信息获取不成功
        if (message == null || !message.isSuccess()) {
            return "forward:/view/fail.jsp";
        }

        SnsAccessTokenApiResult result = message.getEntity();
        if (result == null || !result.isSuccess()) {
            return "forward:/view/fail.jsp";
        }

        String openID = result.getOpenid();
        User user = userServiceBean.getUserWithEnvironment(appID, openID);
        // 没有默认的应用环境
        if (user == null || user.getEnvironment() == null) {
            return "forward:/view/fail.jsp";
        }

        String domain = user.getEnvironment().getDomain();
        State s = stateServiceBean.get(stateId);
        String redirectURI = s.getRedirectURI();
        String path = redirectURI.replaceFirst("http(s)?://[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*(:\\d+)?/", "");
        String redirectURL = domain + (path.startsWith("/") ? "" : "/") + path;
        String responseType = s.getResponseType();
        String state = s.getValue();

        String redirect = "https://open.weixin.qq.com/connect/oauth2/authorize"
                + "?appid=" + appID
                + "&redirect_uri=" + URLEncoder.encode(redirectURL, "UTF-8")
                + "&response_type=" + responseType
                + "&scope=" + result.getScope()
                + "&state=" + URLEncoder.encode(state, "UTF-8")
                + "#wechat_redirect";

        return "redirect:" + redirect;
    }

}
