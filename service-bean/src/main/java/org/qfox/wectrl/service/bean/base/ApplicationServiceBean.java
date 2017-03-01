package org.qfox.wectrl.service.bean.base;

import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.base.ApplicationDAO;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.bean.GenericServiceBean;
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
