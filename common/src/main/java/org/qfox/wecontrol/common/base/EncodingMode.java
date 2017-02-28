package org.qfox.wecontrol.common.base;

/**
 * Created by payne on 2017/1/29.
 */
public enum EncodingMode {
    PLAIN("明文"), ENCRYPTED("密文"), COMPATIBLE("兼容");

    private final String name;

    private EncodingMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
