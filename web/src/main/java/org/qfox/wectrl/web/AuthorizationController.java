package org.qfox.wectrl.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.State;
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
                            Application app) {

        SnsAccessTokenApiResult result = WeixinSnsAPI.INSTANCE.accessToken(app.getAppID(), app.getAppSecret(), code, SnsGrantType.authorization_code);
        result.getOpenid();


        State state = stateServiceBean.get(stateId);

        return null;
    }


}
