package org.qfox.wectrl.service.bean.weixin;

import org.qfox.wectrl.core.weixin.Authorization;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.weixin.AuthorizationDAO;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.qfox.wectrl.service.weixin.AuthorizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangchangpei on 17/3/13.
 */
@Service
public class AuthorizationServiceBean extends GenericServiceBean<Authorization, Long> implements AuthorizationService {

    @Resource
    private AuthorizationDAO authorizationDAO;

    @Override
    protected GenericDAO<Authorization, Long> getEntityDAO() {
        return authorizationDAO;
    }
}
