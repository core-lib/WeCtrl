package org.qfox.wectrl.core.weixin.message;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Entity(name = "WeixinCoordinate")
@Table(name = "weixin_coordinate_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = {"appId", "sender", "timeCreated"})})
public class Coordinate extends Event {
    private static final long serialVersionUID = -2336544674475503537L;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal accuracy;

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(BigDecimal accuracy) {
        this.accuracy = accuracy;
    }
}
