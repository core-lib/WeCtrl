package org.qfox.wectrl.core.weixin.message;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Entity(name = "WeixinShortVideo")
@Table(name = "weixin_short_video_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = {"appId", "msgId"})})
public class ShortVideo extends Message {
    private static final long serialVersionUID = -3208836335716276254L;

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
