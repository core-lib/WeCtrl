package org.qfox.wectrl.service.bean.weixin;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.weixin.UserDAO;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.qfox.wectrl.service.weixin.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    public Page<User> getPagedApplicationUsers(String appID, int pagination, int capacity, String keyword) {
        return userDAO.getPagedApplicationUsers(appID, pagination, capacity, keyword);
    }

    @Override
    public User get(String appID, String openID, String... fetchs) {
        Criteria criteria = userDAO.createCriteria();
        criteria.add(Restrictions.eq("application.appID", appID));
        criteria.add(Restrictions.eq("openID", openID));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for (String fetch : fetchs) {
            criteria.setFetchMode(fetch, FetchMode.JOIN);
        }
        return (User) criteria.uniqueResult();
    }

    @Transactional
    @Override
    public int setUserToEnvironment(String appID, String openID, String envKey) {
        return userDAO.setUserToEnvironment(appID, openID, envKey);
    }

    @Override
    public User getUserWithEnvironment(String appID, String openID) {
        return userDAO.getUserWithEnvironment(appID, openID);
    }
}
