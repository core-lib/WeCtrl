package org.qfox.wectrl.core.weixin.message;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Entity(name = "WeixinView")
@Table(name = "weixin_view_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = {"appId", "sender", "timeCreated"})})
public class View extends Event {
    private static final long serialVersionUID = -6851363512842087810L;

    private String eventKey;
    private String ticket;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @Lob
    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
