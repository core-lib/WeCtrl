package org.qfox.wectrl.core.base;

import org.qfox.wectrl.common.base.ApplicationType;
import org.qfox.wectrl.core.Domain;

import javax.persistence.*;
import java.util.Date;

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
    private Mch merchant;

    private boolean verified;
    private Date dateVerified;

    private boolean pulling; // 正在同步用户
    private boolean refreshing; // 正在刷新Access Token
    private String secret; // 为了屏蔽真正的微信公众号appSecret传输 调用中控服务器时用服务器为每个app生成的secret代替真正的appSecret

    @Column(unique = true, nullable = false, length = 36, updatable = false)
    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    @Column(nullable = false)
    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Column(nullable = false)
    public String getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(String appNumber) {
        this.appNumber = appNumber;
    }

    @Column(nullable = false)
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Column(nullable = false)
    public String getPushURL() {
        return pushURL;
    }

    public void setPushURL(String pushURL) {
        this.pushURL = pushURL;
    }

    @Column(nullable = false)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(nullable = false)
    public String getPortraitURL() {
        return portraitURL;
    }

    public void setPortraitURL(String portraitURL) {
        this.portraitURL = portraitURL;
    }

    @Column(nullable = false)
    public String getQRCodeURL() {
        return QRCodeURL;
    }

    public void setQRCodeURL(String QRCodeURL) {
        this.QRCodeURL = QRCodeURL;
    }

    @Enumerated(EnumType.STRING)
    public ApplicationType getType() {
        return type;
    }

    public void setType(ApplicationType type) {
        this.type = type;
    }

    @Column(unique = true, nullable = false, length = 36, updatable = false)
    public String getOriginalID() {
        return originalID;
    }

    public void setOriginalID(String originalID) {
        this.originalID = originalID;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "mode", column = @Column(name = "encoding_mode")),
            @AttributeOverride(name = "algorithm", column = @Column(name = "encoding_algorithm")),
            @AttributeOverride(name = "password", column = @Column(name = "encoding_password"))
    })
    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "merchant_id")),
            @AttributeOverride(name = "name", column = @Column(name = "merchant_name")),
            @AttributeOverride(name = "username", column = @Column(name = "merchant_username"))
    })
    public Mch getMerchant() {
        return merchant;
    }

    public void setMerchant(Mch merchant) {
        this.merchant = merchant;
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

    public boolean isPulling() {
        return pulling;
    }

    public void setPulling(boolean pulling) {
        this.pulling = pulling;
    }

    public boolean isRefreshing() {
        return refreshing;
    }

    public void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
    }

    @Column(nullable = false, length = 36)
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
