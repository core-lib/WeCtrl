package org.qfox.wectrl.core.weixin;

import org.qfox.wectrl.core.Domain;
import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Env;

import javax.persistence.*;

/**
 * Created by yangchangpei on 17/3/13.
 */
@Entity
@Table(name = "weixin_authorization_tbl")
public class Authorization extends Domain {
    private static final long serialVersionUID = -5706319798867078068L;

    private App application;
    private String redirectURI;
    private String value;
    private String responseType;
    private String scope;
    private String code;
    private String openID;
    private String accessToken;
    private String refreshToken;
    private Long timeExpired;
    private Env environment;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "application_id", updatable = false)),
            @AttributeOverride(name = "appID", column = @Column(name = "application_appID", length = 36, updatable = false)),
            @AttributeOverride(name = "appName", column = @Column(name = "application_appName", updatable = false))
    })
    public App getApplication() {
        return application;
    }

    public void setApplication(App application) {
        this.application = application;
    }

    @Column(updatable = false)
    public String getRedirectURI() {
        return redirectURI;
    }

    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }

    @Column(updatable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(updatable = false)
    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    @Column(updatable = false)
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getTimeExpired() {
        return timeExpired;
    }

    public void setTimeExpired(Long timeExpired) {
        this.timeExpired = timeExpired;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "environment_id")),
            @AttributeOverride(name = "envName", column = @Column(name = "environment_envName")),
            @AttributeOverride(name = "envKey", column = @Column(name = "environment_envKey")),
            @AttributeOverride(name = "domain", column = @Column(name = "environment_domain"))
    })
    public Env getEnvironment() {
        return environment;
    }

    public void setEnvironment(Env environment) {
        this.environment = environment;
    }
}
