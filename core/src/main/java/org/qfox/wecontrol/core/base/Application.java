package org.qfox.wecontrol.core.base;

import org.qfox.wecontrol.common.base.ApplicationType;
import org.qfox.wecontrol.core.Domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yangchangpei on 17/2/27.
 */
@Entity
@Table(name = "base_application_tbl")
public class Application extends Domain {
    private static final long serialVersionUID = 2053916914571283579L;

    private String appID;
    private String appSecret;
    private String appNumber;
    private String appName;
    private String pushURL;
    private String token;
    private String portraitURL;
    private String QRCodeURL;
    private ApplicationType type;
    private String originalID;
    private Encoding encoding;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(String appNumber) {
        this.appNumber = appNumber;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPushURL() {
        return pushURL;
    }

    public void setPushURL(String pushURL) {
        this.pushURL = pushURL;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPortraitURL() {
        return portraitURL;
    }

    public void setPortraitURL(String portraitURL) {
        this.portraitURL = portraitURL;
    }

    public String getQRCodeURL() {
        return QRCodeURL;
    }

    public void setQRCodeURL(String QRCodeURL) {
        this.QRCodeURL = QRCodeURL;
    }

    public ApplicationType getType() {
        return type;
    }

    public void setType(ApplicationType type) {
        this.type = type;
    }

    public String getOriginalID() {
        return originalID;
    }

    public void setOriginalID(String originalID) {
        this.originalID = originalID;
    }

    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }
}
