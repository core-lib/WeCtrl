package org.qfox.wectrl.web.handler;

import org.qfox.wectrl.core.weixin.message.Event;

public interface EventHandler<E extends Event> {

    void handle(E event);

}