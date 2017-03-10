package org.qfox.wectrl.dao.weixin;


import org.qfox.wectrl.core.weixin.message.Message;
import org.qfox.wectrl.dao.GenericDAO;

/**
 * Created by yangchangpei on 17/2/23.
 */
public interface WeixinMessageDAO extends GenericDAO<Message, Long> {

    int merge(Message message) throws Exception;

}
