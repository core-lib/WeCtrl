package org.qfox.wectrl.service.weixin.cgi_bin;

import org.qfox.jestful.client.Client;
import org.qfox.jestful.core.annotation.*;
import org.qfox.wectrl.service.weixin.Language;
import org.qfox.wectrl.service.weixin.cgi_bin.menu.Menu;
import org.qfox.wectrl.service.weixin.cgi_bin.msg.Msg;

/**
 * Created by yangchangpei on 17/2/7.
 */
@Jestful("/cgi-bin")
public interface WeixinCgiBinAPI {

    WeixinCgiBinAPI WECHAT = Client.builder().setProtocol("https").setHost("api.weixin.qq.com").setContentCharsets("UTF-8").addPlugins("characterEncodingPlugin; charset=UTF-8").build().create(WeixinCgiBinAPI.class);
    WeixinCgiBinAPI WXCTRL = Client.builder().setProtocol("https").setHost("api.wxctrl.com").setContentCharsets("UTF-8").addPlugins("characterEncodingPlugin; charset=UTF-8").build().create(WeixinCgiBinAPI.class);

    @GET(value = "/token", produces = "application/json; charset=UTF-8")
    TokenApiResult token(@Query("grant_type") TokenType grantType, @Query("appid") String appId, @Query("secret") String appSecret);

    @GET(value = "/ticket/getticket", produces = "application/json; charset=UTF-8")
    TicketApiResult ticket(@Query("access_token") String accessToken, @Query("type") TicketType type);

    @POST(value = "/message/custom/send", consumes = "application/json", produces = "application/json")
    MessageApiResult message(@Query("access_token") String accessToken, @Body Msg msg);

    @POST(value = "/menu/create", consumes = "application/json", produces = "application/json")
    MenuApiResult menu(@Query("access_token") String accessToken, @Body Menu menu);

    @GET(value = "/user/info", produces = "application/json; charset=UTF-8")
    UserInfoApiResult userInfo(@Query("access_token") String accessToken, @Query("openid") String openID, @Query("lang") Language language);

    @GET(value = "/user/get", produces = "application/json; charset=UTF-8")
    PullApiResult pull(@Query("access_token") String accessToken, @Query("next_openid") String nextID);

}
