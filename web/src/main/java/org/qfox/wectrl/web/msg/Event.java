package org.qfox.wectrl.web.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by yangchangpei on 17/2/22.
 */
public enum Event {

    UNKNOWN("UNKNOWN"), SUBSCRIBE("subscribe"), UNSUBSCRIBE("unsubscribe"), SCAN("SCAN"), LOCATION("LOCATION"), CLICK("CLICK"), VIEW("VIEW"), WIFICONNECTED("WifiConnected");

    private final String value;

    Event(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Event constantOf(String name) {
        try {
            return Event.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return Event.UNKNOWN;
        }
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
