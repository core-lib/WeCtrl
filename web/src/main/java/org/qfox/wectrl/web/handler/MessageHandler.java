package org.qfox.wectrl.web.handler;

import org.qfox.wectrl.core.weixin.message.Message;

public interface MessageHandler<M extends Message> {

    void handle(M message);

}