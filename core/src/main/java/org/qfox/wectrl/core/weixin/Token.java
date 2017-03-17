package org.qfox.wectrl.core.weixin;

import org.qfox.wectrl.common.weixin.WhyInvalid;
import org.qfox.wectrl.core.Domain;
import org.qfox.wectrl.core.base.App;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by payne on 2017/3/5.
 */
@Entity
@Table(name = "weixin_token_tbl")
public class Token extends Domain {
    private static final long serialVersionUID = -4946230534210198042L;

    private String value;
    private Long timeExpired;
    private boolean invalid;
    private Date dateInvalid;
    private WhyInvalid whyInvalid;
    private App application;

    // 实际上失效 离微信服务器端失效还有3分钟
    @Transient
    public boolean isExpiredActually() {
        return value == null || value.isEmpty() || timeExpired == null || (timeExpired - 3L * 60L * 1000L) <= System.currentTimeMillis() || invalid;
    }

    // 逻辑上失效 离微信服务器端失效还有6分钟
    @Transient
    public boolean isExpiredLogically() {
        return value == null || value.isEmpty() || timeExpired == null || (timeExpired - 6L * 60L * 1000L) <= System.currentTimeMillis() || invalid;
    }

    // 剩余秒数
    @Transient
    public int getValidSeconds() {
        return (int) ((timeExpired - 3L * 60L * 1000L - System.currentTimeMillis()) / 1000L);
    }

    @Column(nullable = false, unique = true)
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

    @Override
    public String toString() {
        return value;
    }
}
