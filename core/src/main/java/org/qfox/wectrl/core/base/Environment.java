package org.qfox.wectrl.core.base;

import org.qfox.wectrl.core.Domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Entity
@Table(name = "base_environment_tbl")
public class Environment extends Domain {
    private static final long serialVersionUID = -6989709709063798340L;

    private String envName;
    private String envKey;
    private App application;
    private String domain;
    private String pushURL;
    private boolean verified;
    private Date dateVerified;
    private boolean acquiescent;

    @Column(nullable = false)
    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    @Column(nullable = false)
    public String getEnvKey() {
        return envKey;
    }

    public void setEnvKey(String envKey) {
        this.envKey = envKey;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "application_id")),
            @AttributeOverride(name = "appID", column = @Column(name = "application_appID")),
            @AttributeOverride(name = "appName", column = @Column(name = "application_appName"))
    })
    public App getApplication() {
        return application;
    }

    public void setApplication(App application) {
        this.application = application;
    }

    @Column(nullable = false)
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Column(nullable = false)
    public String getPushURL() {
        return pushURL;
    }

    public void setPushURL(String pushURL) {
        this.pushURL = pushURL;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateVerified() {
        return dateVerified;
    }

    public void setDateVerified(Date dateVerified) {
        this.dateVerified = dateVerified;
    }

    public boolean isAcquiescent() {
        return acquiescent;
    }

    public void setAcquiescent(boolean acquiescent) {
        this.acquiescent = acquiescent;
    }
}
