package org.qfox.wectrl.web.handler;

import org.qfox.wectrl.core.weixin.message.Event;
import org.qfox.wectrl.service.weixin.cgi_bin.msg.Msg;

public interface EventHandler<E extends Event> {

    Msg handle(E event);

}