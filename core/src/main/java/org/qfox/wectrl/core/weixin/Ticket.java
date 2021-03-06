package org.qfox.wectrl.core.weixin;

import org.qfox.wectrl.common.weixin.TicketType;
import org.qfox.wectrl.common.weixin.WhyInvalid;
import org.qfox.wectrl.core.Domain;
import org.qfox.wectrl.core.base.App;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by payne on 2017/3/5.
 */
@Entity
@Table(name = "weixin_ticket_tbl")
public class Ticket extends Domain {
    private static final long serialVersionUID = 6842019942850115316L;

    private String value;
    private Long timeExpired;
    private boolean invalid;
    private Date dateInvalid;
    private WhyInvalid whyInvalid;
    private TicketType type;
    private App application;

    @Transient
    public boolean isExpired() {
        return value == null || value.isEmpty() || timeExpired == null || timeExpired <= System.currentTimeMillis();
    }

    @Transient
    public int getSecondsExpired() {
        return (int) ((timeExpired - System.currentTimeMillis()) / 1000L);
    }

    @Column(nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(nullable = false)
    public Long getTimeExpired() {
        return timeExpired;
    }

    public void setTimeExpired(Long timeExpired) {
        this.timeExpired = timeExpired;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateInvalid() {
        return dateInvalid;
    }

    public void setDateInvalid(Date dateInvalid) {
        this.dateInvalid = dateInvalid;
    }

    @Enumerated(EnumType.STRING)
    public WhyInvalid getWhyInvalid() {
        return whyInvalid;
    }

    public void setWhyInvalid(WhyInvalid whyInvalid) {
        this.whyInvalid = whyInvalid;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "application_id")),
            @AttributeOverride(name = "appID", column = @Column(name = "application_appID", length = 36)),
            @AttributeOverride(name = "appName", column = @Column(name = "application_appName"))
    })
    public App getApplication() {
        return application;
    }

    public void setApplication(App application) {
        this.application = application;
    }
}
