package org.qfox.wectrl.core.weixin.message;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Entity(name = "WeixinScan")
@Table(name = "weixin_scan_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = {"appId", "sender", "timeCreated"})})
public class Scan extends Event {
    private static final long serialVersionUID = 2456022625612199553L;

    private String eventKey;
    private String ticket;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
