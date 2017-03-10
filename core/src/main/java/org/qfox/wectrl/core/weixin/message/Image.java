package org.qfox.wectrl.core.weixin.message;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Entity(name = "WeixinImage")
@Table(name = "weixin_image_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = {"appId", "msgId"})})
public class Image extends Message {
    private static final long serialVersionUID = -1542068249879527564L;

    private Long msgId;
    private String picURL;
    private String mediaId;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
