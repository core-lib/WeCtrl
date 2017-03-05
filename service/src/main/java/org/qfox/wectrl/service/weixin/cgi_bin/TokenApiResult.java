package org.qfox.wectrl.service.weixin.cgi_bin;


import org.qfox.wectrl.service.weixin.ApiResult;

/**
 * Created by yangchangpei on 17/2/7.
 */
public class TokenApiResult extends ApiResult {
    private static final long serialVersionUID = -8916908119532463813L;

    private String access_token;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
