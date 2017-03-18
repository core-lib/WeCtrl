package org.qfox.wectrl.web.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by yangchangpei on 17/2/22.
 */
public enum MsgType {

    UNKNOWN, TEXT, IMAGE, VOICE, VIDEO, SHORTVIDEO, LOCATION, LINK, EVENT;

    @JsonCreator
    public static MsgType constantOf(String name) {
        try {
            return MsgType.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return MsgType.UNKNOWN;
        }
    }

    @JsonValue
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
