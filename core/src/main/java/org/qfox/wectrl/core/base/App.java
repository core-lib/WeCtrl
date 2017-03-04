package org.qfox.wectrl.core.base;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Embeddable
public class App implements Serializable {
    private static final long serialVersionUID = 8672741982601563308L;

    private Long id;
    private String appID;
    private String appName;

    public App() {
    }

    public App(Application application) {
        this.id = application.getId();
        this.appID = application.getAppID();
        this.appName = application.getAppName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
