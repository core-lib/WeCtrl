package org.qfox.wectrl.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.qfox.jestful.client.Message;
import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.wectrl.common.Regexes;
import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.Authorization;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.service.weixin.AuthorizationService;
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
    private AuthorizationService authorizationServiceBean;

    @Resource
    private UserService userServiceBean;

    @GET("/connect/oauth2/authorize")
    public String authorize(@Query("appid") String appID,
                            @Query("redirect_uri") String redirectURI,
                            @Query("response_type") String responseType,
                            @Query("scope") String scope,
                            @Query("state") String state,
                            Application app,
                            HttpServletRequest request) throws JsonProcessingException, UnsupportedEncodingException {
        if (redirectURI == null || !redirectURI.matches(Regexes.PUSH_URL_REGEX)) {
            request.setAttribute("icon", "info");
            request.setAttribute("title", "抱歉");
            request.setAttribute("description", "redirect_uri参数错误");
            return "forward:/view/fail.jsp";
        }

        Authorization authorization = new Authorization();
        authorization.setApplication(new App(app));
        authorization.setRedirectURI(redirectURI);
        authorization.setValue(state);
        authorization.setResponseType(responseType);
        authorization.setScope(scope);
        authorizationServiceBean.save(authorization);

        String scheme = HTTPKit.getClosestScheme(request, "http");
        String domain = request.getServerName();
        String redirectURL = scheme + "//" + domain + "/authorization";

        String result = "https://open.weixin.qq.com/connect/oauth2/authorize"
                + "?appid=" + appID
                + "&redirect_uri=" + URLEncoder.encode(redirectURL, "UTF-8")
                + "&response_type=" + responseType
                + "&scope=" + scope
                + "&state=" + authorization.getId()
                + "#wechat_redirect";

        return "redirect:" + result;
    }

    @GET("/authorization")
    public String authorize(@Query("code") String code,
                            @Query("state") Long stateId,
                            Application app,
                            HttpServletRequest request) throws UnsupportedEncodingException {
        // 如果已经获取该用户的所属环境 那么直接将code和之前的state重定向到提交的所属环境域名的redirectURI路径上
        Authorization authorization = authorizationServiceBean.get(stateId);
        if (authorization.getEnvironment() != null && authorization.getEnvironment().getDomain() != null) {
            String domain = authorization.getEnvironment().getDomain();
            String redirectURI = authorization.getRedirectURI();
            String path = redirectURI.replaceFirst("http(s)?://[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*(:\\d+)?/", "");
            String redirectURL = domain + (path.startsWith("/") ? "" : "/") + path;
            String state = authorization.getValue();
            return "redirect:" + redirectURL + "?code=" + code + (state == null ? "" : "&state=" + URLEncoder.encode(state, "UTF-8"));
        }

        String appID = app.getAppID();
        String appSecret = app.getAppSecret();

        Message<SnsAccessTokenApiResult> message = WeixinSnsAPI.WECHAT.accessToken(appID, appSecret, code, SnsGrantType.authorization_code);
        // 如果用户信息获取不成功
        if (message == null || !message.isSuccess()) {
            return "forward:/view/fail.jsp";
        }

        SnsAccessTokenApiResult result = message.getEntity();
        if (result == null || !result.isSuccess()) {
            request.setAttribute("icon", "info");
            request.setAttribute("title", "抱歉");
            request.setAttribute("description", "获取openID失败:" + (result == null ? "null" : result.getErrmsg()));
            return "forward:/view/fail.jsp";
        }

        String openID = result.getOpenid();
        User user = userServiceBean.getUserWithEnvironment(appID, openID);
        // 没有默认的应用环境
        if (user == null || user.getEnvironment() == null) {
            request.setAttribute("icon", "warn");
            request.setAttribute("title", "抱歉");
            request.setAttribute("description", "请添加默认应用环境");
            return "forward:/view/fail.jsp";
        }

        authorization.setCode(code);
        authorization.setOpenID(result.getOpenid());
        authorization.setAccessToken(result.getAccess_token());
        authorization.setRefreshToken(result.getRefresh_token());
        authorization.setTimeExpired(System.currentTimeMillis() + result.getExpires_in() * 1000L);
        authorization.setEnvironment(user.getEnvironment());
        authorizationServiceBean.update(authorization);

        String scheme = HTTPKit.getClosestScheme(request, "http");
        String domain = request.getServerName();
        String redirectURL = scheme + "//" + domain + "/authorization";

        // 申请再次授权获取code给真正的redirectURI
        String responseType = authorization.getResponseType();
        String scope = authorization.getScope();
        String redirect = "https://open.weixin.qq.com/connect/oauth2/authorize"
                + "?appid=" + appID
                + "&redirect_uri=" + URLEncoder.encode(redirectURL, "UTF-8")
                + "&response_type=" + responseType
                + "&scope=" + scope
                + "&state=" + authorization.getId()
                + "#wechat_redirect";

        return "redirect:" + redirect;
    }

}
