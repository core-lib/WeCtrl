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

    public Env() {
    }

    public Env(Environment environment) {
        this.id = environment.getId();
        this.envName = environment.getEnvName();
        this.envKey = environment.getEnvKey();
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
}
