package org.qfox.wectrl.core.weixin.message;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Entity(name = "WeixinLink")
@Table(name = "weixin_link_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = {"appId", "msgId"})})
public class Link extends Message {
    private static final long serialVersionUID = 2709469929906789304L;

    private Long msgId;
    private String title;
    private String description;
    private String url;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Lob
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
