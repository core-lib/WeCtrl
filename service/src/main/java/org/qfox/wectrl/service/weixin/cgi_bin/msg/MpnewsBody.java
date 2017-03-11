package org.qfox.wectrl.service.weixin.cgi_bin.msg;

import java.io.Serializable;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class MpnewsBody implements Serializable {
    private static final long serialVersionUID = 3594191654012680638L;

    private String media_id;

    public MpnewsBody() {
    }

    public MpnewsBody(String media_id) {
        this.media_id = media_id;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
}
