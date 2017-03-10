package org.qfox.wectrl.core.weixin.message;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Entity(name = "WeixinLocation")
@Table(name = "weixin_location_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = {"appId", "msgId"})})
public class Location extends Message {
    private static final long serialVersionUID = -4008617271409844280L;

    private Long msgId;
    private BigDecimal locationX;
    private BigDecimal locationY;
    private BigDecimal scale;
    private String label;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public BigDecimal getLocationX() {
        return locationX;
    }

    public void setLocationX(BigDecimal locationX) {
        this.locationX = locationX;
    }

    public BigDecimal getLocationY() {
        return locationY;
    }

    public void setLocationY(BigDecimal locationY) {
        this.locationY = locationY;
    }

    public BigDecimal getScale() {
        return scale;
    }

    public void setScale(BigDecimal scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
