package org.qfox.wectrl.core.base;

import org.qfox.wectrl.core.Domain;

import javax.persistence.*;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Entity
@Table(name = "base_merchant_tbl")
public class Merchant extends Domain {
    private static final long serialVersionUID = 1852209583520216208L;

    private String name;
    private String username;
    private String password;
    private String email;
    private String cellphone;
    private Picture logo;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, unique = true, updatable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH}, optional = true)
    public Picture getLogo() {
        return logo;
    }

    public void setLogo(Picture logo) {
        this.logo = logo;
    }
}
