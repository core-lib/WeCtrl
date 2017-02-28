package org.qfox.wecontrol.service.bean.base;

import org.qfox.wecontrol.core.base.Application;
import org.qfox.wecontrol.dao.GenericDAO;
import org.qfox.wecontrol.dao.base.ApplicationDAO;
import org.qfox.wecontrol.service.base.ApplicationService;
import org.qfox.wecontrol.service.bean.GenericServiceBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangchangpei on 17/2/28.
 */
@Service
public class ApplicationServiceBean extends GenericServiceBean<Application, Long> implements ApplicationService {

    @Resource
    private ApplicationDAO applicationDAO;

    @Override
    protected GenericDAO<Application, Long> getEntityDAO() {
        return applicationDAO;
    }
}
