package org.qfox.wectrl.core.weixin;

import org.qfox.wectrl.core.Domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yangchangpei on 17/3/13.
 */
@Entity
@Table(name = "weixin_state_tbl")
public class State extends Domain {
    private static final long serialVersionUID = -5706319798867078068L;

    private String redirectURI;
    private String value;

    public String getRedirectURI() {
        return redirectURI;
    }

    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
