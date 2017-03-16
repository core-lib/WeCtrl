package org.qfox.wectrl.web;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.POST;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.weixin.TokenService;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenType;
import org.qfox.wectrl.web.auth.Authorized;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by payne on 2017/3/15.
 */
@Jestful("/")
@Controller
public class TokenController {

    @Resource
    private ApplicationService applicationServiceBean;

    @Resource
    private TokenService tokenServiceBean;

    @Authorized(required = false)
    @GET(value = "/cgi-bin/token", produces = "application/json; charset=UTF-8")
    public TokenApiResult get(@Query("grant_type") TokenType grantType, @Query("appid") String appID, @Query("secret") String secret) {
        TokenApiResult result = check(appID, secret);
        return result != null ? result : tokenServiceBean.getApplicationAccessToken(appID);
    }

    @Authorized(required = false)
    @POST(value = "/cgi-bin/token", produces = "application/json; charset=UTF-8")
    public TokenApiResult post(@Query("grant_type") TokenType grantType, @Query("appid") String appID, @Query("secret") String secret) {
        TokenApiResult result = check(appID, secret);
        return result != null ? result : tokenServiceBean.newApplicationAccessToken(appID);
    }

    private TokenApiResult check(@Query("appid") String appID, @Query("secret") String secret) {
        Application app = applicationServiceBean.getApplicationByAppID(appID);

        if (app == null) {
            TokenApiResult result = new TokenApiResult();
            result.setErrcode(40013);
            result.setAccess_token("invalid appid");
            return result;
        }

        if (!app.getSecret().equals(secret)) {
            TokenApiResult result = new TokenApiResult();
            result.setErrcode(40001);
            result.setAccess_token("invalid secret");
            return result;
        }

        return null;
    }

}
