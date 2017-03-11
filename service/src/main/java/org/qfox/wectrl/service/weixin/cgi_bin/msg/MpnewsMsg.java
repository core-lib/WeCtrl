package org.qfox.wectrl.service.weixin.cgi_bin.msg;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class MpnewsMsg extends Msg {
    private static final long serialVersionUID = 4497819549963023634L;

    private MpnewsBody mpnews;

    public MpnewsMsg() {
        super("mpnews");
    }

    public MpnewsMsg(String mediaId) {
        this();
        this.mpnews = new MpnewsBody(mediaId);
    }

    public MpnewsBody getMpnews() {
        return mpnews;
    }

    public void setMpnews(MpnewsBody mpnews) {
        this.mpnews = mpnews;
    }
}
