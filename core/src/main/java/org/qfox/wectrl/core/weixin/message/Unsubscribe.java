package org.qfox.wectrl.core.weixin.message;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Entity(name = "WeixinUnsubscribe")
@Table(name = "weixin_unsubscribe_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = {"appId", "sender", "timeCreated"})})
public class Unsubscribe extends Event {
    private static final long serialVersionUID = 4096809365594400623L;

}
