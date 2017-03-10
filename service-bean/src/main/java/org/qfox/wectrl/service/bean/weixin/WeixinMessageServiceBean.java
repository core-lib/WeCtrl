package org.qfox.wectrl.service.bean.weixin;

import org.qfox.wectrl.core.weixin.message.Message;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.weixin.WeixinMessageDAO;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.qfox.wectrl.service.weixin.WeixinMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Service
public class WeixinMessageServiceBean extends GenericServiceBean<Message, Long> implements WeixinMessageService {

    @Resource
    private WeixinMessageDAO weixinMessageDAO;

    @Override
    protected GenericDAO<Message, Long> getEntityDAO() {
        return weixinMessageDAO;
    }

    @Override
    public int merge(Message message) throws Exception {
        return weixinMessageDAO.merge(message);
    }
}
