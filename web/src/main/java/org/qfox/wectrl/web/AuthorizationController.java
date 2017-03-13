package org.qfox.wectrl.web;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.Query;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangchangpei on 17/3/13.
 */
@Jestful("/")
@Controller
public class AuthorizationController {

    @GET("/connect/oauth2/authorize")
    public String authorize(@Query("appid") String appID,
                            @Query("redirect_uri") String redirectURI,
                            @Query("response_type") String responseType,
                            @Query("scope") String scope,
                            @Query("state") String state,
                            HttpServletRequest request) {



        return null;
    }

}
