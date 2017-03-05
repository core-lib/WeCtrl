package org.qfox.wectrl.service.weixin.cgi_bin;

import org.qfox.jestful.core.annotation.*;
import org.qfox.wectrl.service.weixin.Language;

/**
 * Created by yangchangpei on 17/2/7.
 */
@Jestful("/cgi-bin")
public interface WeixinCgiBinAPI {

    @GET(value = "/token", produces = "application/json; charset=UTF-8")
    TokenApiResult token(@Query("grant_type") TokenType grantType, @Query("appid") String appId, @Query("secret") String appSecret);

    @GET(value = "/ticket/getticket", produces = "application/json; charset=UTF-8")
    TicketApiResult ticket(@Query("access_token") String accessToken, @Query("type") TicketType type);

    @POST(value = "/message/custom/send")
    MessageApiResult message(@Query("access_token") String accessToken, @Body MessageApiParameter parameter);

    @POST(value = "/menu/create", consumes = "application/json", produces = "application/json")
    MenuApiResult menu(@Query("access_token") String accessToken, @Body MenuApiParameter parameter);

    @GET(value = "/user/info", produces = "application/json; charset=UTF-8")
    UserInfoApiResult userInfo(@Query("access_token") String accessToken, @Query("openid") String openID, @Query("lang") Language language);

}