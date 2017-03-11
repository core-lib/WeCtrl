package org.qfox.wectrl.dao.weixin;


import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.core.weixin.message.Image;
import org.qfox.wectrl.core.weixin.message.Message;
import org.qfox.wectrl.core.weixin.message.Text;
import org.qfox.wectrl.dao.GenericDAO;

import java.util.Map;

/**
 * Created by yangchangpei on 17/2/23.
 */
public interface WeixinMessageDAO extends GenericDAO<Message, Long> {

    int merge(Message message) throws Exception;

    Page<Text> getPagedApplicationTexts(String appID, int pagination, int capacity, String keyword);

    Page<Image> getPagedApplicationImages(String appID, int pagination, int capacity, String keyword);
}
