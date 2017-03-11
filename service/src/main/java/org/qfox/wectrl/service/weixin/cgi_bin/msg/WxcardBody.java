package org.qfox.wectrl.service.weixin.cgi_bin.msg;

import java.io.Serializable;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class WxcardBody implements Serializable {
    private static final long serialVersionUID = 6676071897901924767L;

    private String card_id;

    public WxcardBody() {
    }

    public WxcardBody(String card_id) {
        this.card_id = card_id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }
}
