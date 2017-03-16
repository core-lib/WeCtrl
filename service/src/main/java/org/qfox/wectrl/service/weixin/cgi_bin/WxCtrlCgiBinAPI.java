package org.qfox.wectrl.service.weixin.cgi_bin;

import org.qfox.jestful.client.Client;
import org.qfox.jestful.client.Message;
import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.POST;
import org.qfox.jestful.core.annotation.Query;

/**
 * Created by yangchangpei on 17/3/16.
 */
@Jestful("/cgi-bin")
public interface WxCtrlCgiBinAPI {

    WxCtrlCgiBinAPI INSTANCE = Client.builder().setProtocol("http").setHost("localhost").setPort(8081).setContentCharsets("UTF-8").addPlugins("characterEncodingPlugin; charset=UTF-8").build().create(WxCtrlCgiBinAPI.class);

    @GET(value = "/token", produces = "application/json; charset=UTF-8")
    Message<TokenApiResult> getToken(@Query("grant_type") TokenType grantType, @Query("appid") String appId, @Query("secret") String appSecret);

    @POST(value = "/token", produces = "application/json; charset=UTF-8")
    Message<TokenApiResult> newToken(@Query("grant_type") TokenType grantType, @Query("appid") String appId, @Query("secret") String appSecret);

    @GET(value = "/ticket/getticket", produces = "application/json; charset=UTF-8")
    Message<TicketApiResult> getTicket(@Query("access_token") String accessToken, @Query("type") TicketType type);

    @POST(value = "/ticket/getticket", produces = "application/json; charset=UTF-8")
    Message<TicketApiResult> newTicket(@Query("access_token") String accessToken, @Query("type") TicketType type);

}
