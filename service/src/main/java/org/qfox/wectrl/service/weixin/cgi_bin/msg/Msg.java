package org.qfox.wectrl.service.weixin.cgi_bin.msg;

import java.io.Serializable;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class Msg implements Serializable {
    private static final long serialVersionUID = 1681461787010003061L;

    private final String msgtype;
    private String touser;

    protected Msg(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }
}
