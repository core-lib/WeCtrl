package org.qfox.wectrl.service.bean.base;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Verification;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.base.VerificationDAO;
import org.qfox.wectrl.service.base.VerificationService;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public Page<Verification> getPagedMerchantVerifications(Long merchantId, int pagination, int capacity) {
        Page<Verification> page = new Page<>(pagination, capacity);

        Criteria criteria = verificationDAO.createCriteria();
        criteria.add(Restrictions.eq("merchant.id", merchantId));
        criteria.add(Restrictions.eq("deleted", false));
        criteria.setProjection(Projections.countDistinct("id"));
        Object total = criteria.uniqueResult();
        page.setTotal(total == null ? 0 : Integer.valueOf(total.toString()));

        if (page.getTotal() > 0 && page.getTotal() > pagination * capacity) {
            criteria.setProjection(null);
            criteria.setFirstResult(pagination * capacity);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(capacity);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Verification> verifications = criteria.list();
            page.setEntities(verifications);
        }

        return page;
    }
}
