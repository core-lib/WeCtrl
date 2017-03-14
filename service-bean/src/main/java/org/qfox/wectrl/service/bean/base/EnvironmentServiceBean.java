package org.qfox.wectrl.service.bean.base;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Environment;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.base.EnvironmentDAO;
import org.qfox.wectrl.service.base.EnvironmentService;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Service
public class EnvironmentServiceBean extends GenericServiceBean<Environment, Long> implements EnvironmentService {

    @Resource
    private EnvironmentDAO environmentDAO;

    @Override
    protected GenericDAO<Environment, Long> getEntityDAO() {
        return environmentDAO;
    }


    @Override
    public Page<Environment> getPagedApplicationEnvironments(String appID, int pagination, int capacity) {
        Page<Environment> page = new Page<>(pagination, capacity);

        Criteria criteria = environmentDAO.createCriteria();
        criteria.add(Restrictions.eq("application.appID", appID));
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
            List<Environment> environments = criteria.list();
            page.setEntities(environments);
        }

        return page;
    }

    @Override
    public boolean isApplicationEnvKeyExisted(String appID, String envKey) {
        Criteria criteria = environmentDAO.createCriteria();
        criteria.setProjection(Projections.count("id"));
        criteria.add(Restrictions.eq("application.appID", appID));
        criteria.add(Restrictions.eq("envKey", envKey));
        Object count = criteria.uniqueResult();
        return count != null && Integer.valueOf(count.toString()) > 0;
    }

    @Override
    public Environment getApplicationEnvironment(String appID, String envKey, String... fetchs) {
        Criteria criteria = environmentDAO.createCriteria();
        criteria.add(Restrictions.eq("application.appID", appID));
        criteria.add(Restrictions.eq("envKey", envKey));
        criteria.add(Restrictions.eq("deleted", false));
        for (String fetch : fetchs) {
            criteria.setFetchMode(fetch, FetchMode.JOIN);
        }
        return (Environment) criteria.uniqueResult();
    }

    @Transactional
    @Override
    public int updateToVerified(String appID, String envKey) {
        return environmentDAO.updateToVerified(appID, envKey);
    }

    @Transactional
    @Override
    public void save(Environment environment) {
        if (environment.isAcquiescent()) {
            environmentDAO.updateToNormal(environment.getApplication().getAppID());
        }
        // 如果唯一约束冲突证明并发了 那么更新回去就OK了
        environmentDAO.merge(environment, "envName", "domain", "pushURL", "acquiescent");
    }

    @Transactional
    @Override
    public int update(String oldEnvKey, Environment environment) {
        if (!oldEnvKey.equals(environment.getEnvKey())) {
            environmentDAO.deleteByAppIDAndEnvKey(environment.getApplication().getAppID(), oldEnvKey);
        }
        if (environment.isAcquiescent()) {
            environmentDAO.updateToNormal(environment.getApplication().getAppID());
        }
        // 如果唯一约束冲突证明并发了或者是没有修改envKey 那么更新回去就OK了
        return environmentDAO.merge(environment, "envName", "domain", "pushURL", "acquiescent");
    }

}
