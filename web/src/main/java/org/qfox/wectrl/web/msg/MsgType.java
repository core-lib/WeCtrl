package org.qfox.wectrl.web.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by yangchangpei on 17/2/22.
 */
public enum MsgType {

    UNKNOWN("UNKNOWN"), TEXT("text"), IMAGE("image"), VOICE("voice"), VIDEO("video"), SHORTVIDEO("shortvideo"), LOCATION("location"), LINK("link"), EVENT("event");

    private final String value;

    MsgType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static MsgType constantOf(String name) {
        try {
            return MsgType.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return MsgType.UNKNOWN;
        }
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
