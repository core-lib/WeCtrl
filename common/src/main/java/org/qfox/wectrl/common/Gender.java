package org.qfox.wectrl.common;

/**
 * Created by payne on 2017/1/30.
 */
public enum Gender {
    UNKNOWN("未知"), MALE("男"), FEMALE("女");

    private final String name;

    private Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
