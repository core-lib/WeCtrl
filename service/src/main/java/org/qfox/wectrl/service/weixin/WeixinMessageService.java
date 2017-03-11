package org.qfox.wectrl.service.weixin;


import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.weixin.message.Message;
import org.qfox.wectrl.core.weixin.message.Text;
import org.qfox.wectrl.service.GenericService;

/**
 * Created by yangchangpei on 17/2/23.
 */
public interface WeixinMessageService extends GenericService<Message, Long> {

    int merge(Message message) throws Exception;

    Page<Text> getPagedApplicationTexts(String appID, int pagination, int capacity, String keyword);

}
