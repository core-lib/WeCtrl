package org.qfox.wectrl.service.weixin.sns;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.wectrl.service.weixin.Language;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Jestful("/sns")
public interface WeixinSnsAPI {

    @GET(value = "/oauth2/access_token", produces = "application/json; charset=UTF-8")
    SnsAccessTokenApiResult accessToken(@Query("appid") String appID, @Query("secret") String appSecret, @Query("code") String code, @Query("grant_type") SnsGrantType grantType);

    @GET(value = "/userinfo", produces = "application/json; charset=UTF-8")
    SnsUserInfoApiResult userInfo(@Query("access_token") String accessToken, @Query("openid") String openID, @Query("lang") Language language);

}
