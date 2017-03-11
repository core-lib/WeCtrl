package org.qfox.wectrl.service.weixin.cgi_bin.msg;

import java.io.Serializable;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class VoiceBody implements Serializable {
    private static final long serialVersionUID = 5612822432536839426L;

    private String media_id;

    public VoiceBody() {
    }

    public VoiceBody(String media_id) {
        this.media_id = media_id;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
}
