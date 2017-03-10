package org.qfox.wectrl.core.weixin.message;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Entity(name = "WeixinVideo")
@Table(name = "weixin_video_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = {"appId", "msgId"})})
public class Video extends Message {
    private static final long serialVersionUID = 8190763732842074893L;

    private Long msgId;
    private String thumbMediaId;
    private String mediaId;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
