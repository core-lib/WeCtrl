package org.qfox.wecontrol.common.base;

/**
 * Created by yangchangpei on 17/2/22.
 */
public enum ApplicationType {

    SERVICE("服务号"), SUBSCRIPTION("订阅号"), ENTERPRISE("企业号");

    private final String name;

    ApplicationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
