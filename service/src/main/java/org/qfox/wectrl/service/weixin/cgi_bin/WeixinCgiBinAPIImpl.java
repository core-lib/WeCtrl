package org.qfox.wectrl.service.weixin.cgi_bin;

import org.qfox.jestful.client.Client;
import org.qfox.jestful.core.annotation.Body;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.wectrl.service.weixin.Language;
import org.springframework.stereotype.Component;

/**
 * Created by payne on 2017/3/7.
 */
@Jestful("/cgi-bin")
@Component
public class WeixinCgiBinAPIImpl implements WeixinCgiBinAPI {
    private final WeixinCgiBinAPI weixinCgiBinAPI = Client.builder().setProtocol("https").setHost("api.weixin.qq.com").setContentCharsets("UTF-8").addPlugins("characterEncodingPlugin; charset=UTF-8").build().create(WeixinCgiBinAPI.class);

    @Override
    public TokenApiResult token(@Query("grant_type") TokenType grantType, @Query("appid") String appId, @Query("secret") String appSecret) {
        return weixinCgiBinAPI.token(grantType, appId, appSecret);
    }

    @Override
    public TicketApiResult ticket(@Query("access_token") String accessToken, @Query("type") TicketType type) {
        return weixinCgiBinAPI.ticket(accessToken, type);
    }

    @Override
    public MessageApiResult message(@Query("access_token") String accessToken, @Body MessageApiParameter parameter) {
        return weixinCgiBinAPI.message(accessToken, parameter);
    }

    @Override
    public MenuApiResult menu(@Query("access_token") String accessToken, @Body MenuApiParameter parameter) {
        return weixinCgiBinAPI.menu(accessToken, parameter);
    }

    @Override
    public UserInfoApiResult userInfo(@Query("access_token") String accessToken, @Query("openid") String openID, @Query("lang") Language language) {
        return weixinCgiBinAPI.userInfo(accessToken, openID, language);
    }

    @Override
    public PullApiResult pull(@Query("access_token") String accessToken, @Query("next_openid") String nextID) {
        return weixinCgiBinAPI.pull(accessToken, nextID);
    }
}
