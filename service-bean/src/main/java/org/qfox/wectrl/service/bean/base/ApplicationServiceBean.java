package org.qfox.wectrl.service.bean.base;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.base.ApplicationDAO;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public Page<Application> getPagedMerchantApplications(Long merchantId, int pagination, int capacity) {
        Page<Application> page = new Page<>(pagination, capacity);

        Criteria criteria = applicationDAO.createCriteria();
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
            List<Application> applications = criteria.list();
            page.setEntities(applications);
        }

        return page;
    }

    @Override
    public boolean isAppIDExisted(String appID) {
        Criteria criteria = applicationDAO.createCriteria();
        criteria.setProjection(Projections.count("id"));
        criteria.add(Restrictions.eq("appID", appID));
        Object count = criteria.uniqueResult();
        return count != null && Integer.valueOf(count.toString()) > 0;
    }


    @Override
    public boolean isOriginalIDExisted(String originalID) {
        Criteria criteria = applicationDAO.createCriteria();
        criteria.setProjection(Projections.count("id"));
        criteria.add(Restrictions.eq("originalID", originalID));
        Object count = criteria.uniqueResult();
        return count != null && Integer.valueOf(count.toString()) > 0;
    }

    @Override
    public Application getApplicationByAppID(String appID, String... fetchs) {
        Criteria criteria = applicationDAO.createCriteria();
        criteria.add(Restrictions.eq("appID", appID));
        criteria.add(Restrictions.eq("deleted", false));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for (String fetch : fetchs) {
            criteria.setFetchMode(fetch, FetchMode.JOIN);
        }
        return (Application) criteria.uniqueResult();
    }

    @Transactional
    @Override
    public boolean startPulling(String appID) {
        return applicationDAO.startPulling(appID);
    }

    @Transactional
    @Override
    public boolean endPulling(String appID) {
        return applicationDAO.endPulling(appID);
    }

    @Transactional
    @Override
    public int updateToVerified(String appID) {
        return applicationDAO.updateToVerified(appID);
    }
}
