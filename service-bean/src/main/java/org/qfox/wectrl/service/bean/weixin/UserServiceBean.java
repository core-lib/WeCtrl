package org.qfox.wectrl.service.bean.weixin;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Environment;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.weixin.UserDAO;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.qfox.wectrl.service.weixin.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by payne on 2017/3/6.
 */
@Service
public class UserServiceBean extends GenericServiceBean<User, Long> implements UserService {

    @Resource
    private UserDAO userDAO;

    @Override
    protected GenericDAO<User, Long> getEntityDAO() {
        return userDAO;
    }

    @Override
    public Page<User> getPagedApplicationUsers(String appID, int pagination, int capacity) {
        Page<User> page = new Page<>(pagination, capacity);

        Criteria criteria = userDAO.createCriteria();
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
            List<User> environments = criteria.list();
            page.setEntities(environments);
        }

        return page;
    }

}
