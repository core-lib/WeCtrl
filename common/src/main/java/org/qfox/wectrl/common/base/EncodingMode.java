package org.qfox.wectrl.common.base;

/**
 * Created by payne on 2017/1/29.
 */
public enum EncodingMode {
    PLAIN("明文模式"), COMPATIBLE("兼容模式"), ENCRYPTED("安全模式");

    private final String name;

    private EncodingMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
