package org.qfox.wectrl.core.weixin;

import org.qfox.wectrl.common.Gender;
import org.qfox.wectrl.core.Domain;
import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Env;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by payne on 2017/3/6.
 */
@Entity
@Table(name = "weixin_user_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = {"application_appID", "openID"})})
public class User extends Domain {
    private static final long serialVersionUID = -4162990839957654501L;

    private App application;
    private boolean subscribed;
    private String openID;
    private String nickname;
    private Gender gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String portraitURL;
    private Date dateSubscribed;
    private String unionID;
    private String remark;
    private Integer groupID;
    private Env environment;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "application_id")),
            @AttributeOverride(name = "appID", column = @Column(name = "application_appID", length = 36)),
            @AttributeOverride(name = "appName", column = @Column(name = "application_appName"))
    })
    public App getApplication() {
        return application;
    }

    public void setApplication(App application) {
        this.application = application;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Column(nullable = false, length = 36)
    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column(length = 12)
    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPortraitURL() {
        return portraitURL;
    }

    public void setPortraitURL(String portraitURL) {
        this.portraitURL = portraitURL;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateSubscribed() {
        return dateSubscribed;
    }

    public void setDateSubscribed(Date dateSubscribed) {
        this.dateSubscribed = dateSubscribed;
    }

    public String getUnionID() {
        return unionID;
    }

    public void setUnionID(String unionID) {
        this.unionID = unionID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "environment_id")),
            @AttributeOverride(name = "envName", column = @Column(name = "environment_envName")),
            @AttributeOverride(name = "envKey", column = @Column(name = "environment_envKey"))
    })
    public Env getEnvironment() {
        return environment;
    }

    public void setEnvironment(Env environment) {
        this.environment = environment;
    }
}
