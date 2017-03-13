package org.qfox.wectrl.service.bean.weixin;

import org.qfox.wectrl.core.weixin.State;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.weixin.StateDAO;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.qfox.wectrl.service.weixin.StateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangchangpei on 17/3/13.
 */
@Service
public class StateServiceBean extends GenericServiceBean<State, Long> implements StateService {

    @Resource
    private StateDAO stateDAO;

    @Override
    protected GenericDAO<State, Long> getEntityDAO() {
        return stateDAO;
    }
}
