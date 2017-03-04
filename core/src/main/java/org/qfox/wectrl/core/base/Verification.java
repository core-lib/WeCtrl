package org.qfox.wectrl.core.base;

import org.qfox.wectrl.core.Domain;

import javax.persistence.*;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Entity
@Table(name = "base_verification_tbl")
public class Verification extends Domain {
    private static final long serialVersionUID = -4348740977711268961L;

    private App application;
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;

    private String token;
    private Encoding encoding;

    private boolean success;

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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
