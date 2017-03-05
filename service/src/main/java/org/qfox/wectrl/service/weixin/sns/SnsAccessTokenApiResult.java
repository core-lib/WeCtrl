package org.qfox.wectrl.service.weixin.sns;


import org.qfox.wectrl.service.weixin.ApiResult;

/**
 * Created by yangchangpei on 17/2/23.
 */
public class SnsAccessTokenApiResult extends ApiResult {
    private static final long serialVersionUID = 7126398286843800318L;

    private String access_token;
    private Long expires_in;
    private String refresh_token;
    private String openid;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
