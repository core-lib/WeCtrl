package org.qfox.wectrl.service.weixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created by yangchangpei on 17/2/7.
 */
public class ApiResult implements Serializable {
    private static final long serialVersionUID = -4249110104199105203L;

    protected int errcode;
    protected String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return errcode == 0;
    }
}
