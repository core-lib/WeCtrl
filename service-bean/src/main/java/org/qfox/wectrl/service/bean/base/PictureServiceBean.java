package org.qfox.wectrl.service.bean.base;

import org.qfox.wectrl.core.base.Picture;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.base.PictureDAO;
import org.qfox.wectrl.service.base.PictureService;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Service
public class PictureServiceBean extends GenericServiceBean<Picture, Long> implements PictureService {

    @Resource
    private PictureDAO pictureDAO;

    @Override
    protected GenericDAO<Picture, Long> getEntityDAO() {
        return pictureDAO;
    }
}
