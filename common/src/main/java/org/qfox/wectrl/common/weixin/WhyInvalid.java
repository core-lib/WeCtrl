package org.qfox.wectrl.common.weixin;

/**
 * Created by payne on 2017/3/5.
 */
public enum WhyInvalid {
    EXPIRED("已超时"), RESET("已重设");

    private final String name;

    WhyInvalid(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
