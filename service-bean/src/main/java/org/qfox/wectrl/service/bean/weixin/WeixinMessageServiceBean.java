package org.qfox.wectrl.service.bean.weixin;

import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.weixin.message.Image;
import org.qfox.wectrl.core.weixin.message.Message;
import org.qfox.wectrl.core.weixin.message.Text;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.weixin.WeixinMessageDAO;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.qfox.wectrl.service.weixin.WeixinMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public int merge(Message message) throws Exception {
        return weixinMessageDAO.merge(message);
    }

    @Override
    public Page<Text> getPagedApplicationTexts(String appID, int pagination, int capacity, String keyword) {
        return weixinMessageDAO.getPagedApplicationTexts(appID, pagination, capacity, keyword);
    }

    @Override
    public Page<Image> getPagedApplicationImages(String appID, int pagination, int capacity, String keyword) {
        return weixinMessageDAO.getPagedApplicationImages(appID, pagination, capacity, keyword);
    }
}
