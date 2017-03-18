package org.qfox.wectrl.web.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by yangchangpei on 17/2/22.
 */
public enum Event {

    UNKNOWN, SUBSCRIBE, UNSUBSCRIBE, SCAN, LOCATION, CLICK, VIEW;

    @JsonCreator
    public static Event constantOf(String name) {
        try {
            return Event.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return Event.UNKNOWN;
        }
    }

    @JsonValue
    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
