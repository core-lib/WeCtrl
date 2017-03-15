package org.qfox.wectrl.service.weixin;

import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.weixin.Token;
import org.qfox.wectrl.service.GenericService;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenApiResult;

/**
 * Created by payne on 2017/3/5.
 */
public interface TokenService extends GenericService<Token, Long> {

    TokenApiResult getApplicationAccessToken(String appID);

    TokenApiResult newApplicationAccessToken(String appID);

    Page<Token> getPagedApplicationTokens(String appID, int pagination, int capacity);

    Token getTokenByValue(String value);

}
