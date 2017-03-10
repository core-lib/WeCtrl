package org.qfox.wectrl.core.weixin.message;

import org.qfox.wectrl.core.Domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by yangchangpei on 17/2/23.
 */
@MappedSuperclass
public abstract class Message extends Domain {
    private static final long serialVersionUID = 5248063476596516097L;

    private String appId;
    private String receiver;
    private String sender;
    private Long timeCreated;

    @Column(nullable = false, length = 32)
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(nullable = false, length = 32)
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated) {
        this.timeCreated = timeCreated;
    }

}
