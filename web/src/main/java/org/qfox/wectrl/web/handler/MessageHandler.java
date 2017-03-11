package org.qfox.wectrl.web.handler;

import org.qfox.wectrl.core.weixin.message.Message;
import org.qfox.wectrl.service.weixin.cgi_bin.msg.Msg;

public interface MessageHandler<M extends Message> {

    Msg handle(M message);

}