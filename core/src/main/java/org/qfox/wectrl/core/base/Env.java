package org.qfox.wectrl.core.base;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by yangchangpei on 17/3/13.
 */
@Embeddable
public class Env implements Serializable {
    private static final long serialVersionUID = -1646754554252948317L;

    private Long id;
    private String envName;
    private String envKey;
    private String domain;
    private String pushURL;

    public Env() {
    }

    public Env(Environment environment) {
        this.id = environment.getId();
        this.envName = environment.getEnvName();
        this.envKey = environment.getEnvKey();
        this.domain = environment.getDomain();
        this.pushURL = environment.getPushURL();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getEnvKey() {
        return envKey;
    }

    public void setEnvKey(String envKey) {
        this.envKey = envKey;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPushURL() {
        return pushURL;
    }

    public void setPushURL(String pushURL) {
        this.pushURL = pushURL;
    }
}
