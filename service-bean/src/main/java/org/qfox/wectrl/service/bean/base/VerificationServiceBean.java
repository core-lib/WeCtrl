package org.qfox.wectrl.service.bean.base;

import org.qfox.wectrl.core.base.Verification;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.base.VerificationDAO;
import org.qfox.wectrl.service.base.VerificationService;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Service
public class VerificationServiceBean extends GenericServiceBean<Verification, Long> implements VerificationService {

    @Resource
    private VerificationDAO verificationDAO;

    @Override
    protected GenericDAO<Verification, Long> getEntityDAO() {
        return verificationDAO;
    }
}
