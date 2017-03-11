package org.qfox.wectrl.service.weixin.cgi_bin.msg;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class WxcardMsg extends Msg {
    private static final long serialVersionUID = 2135700428019540145L;

    private WxcardBody wxcard;

    public WxcardMsg() {
        super("wxcard");
    }

    public WxcardMsg(String cardId) {
        this();
        this.wxcard = new WxcardBody(cardId);
    }

    public WxcardBody getWxcard() {
        return wxcard;
    }

    public void setWxcard(WxcardBody wxcard) {
        this.wxcard = wxcard;
    }
}
