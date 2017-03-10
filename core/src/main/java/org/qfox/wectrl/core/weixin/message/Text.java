package org.qfox.wectrl.core.weixin.message;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Entity(name = "WeixinText")
@Table(name = "weixin_text_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = {"appId", "msgId"})})
public class Text extends Message {
    private static final long serialVersionUID = -7105522123113583159L;

    private Long msgId;
    private String content;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    @Lob
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
