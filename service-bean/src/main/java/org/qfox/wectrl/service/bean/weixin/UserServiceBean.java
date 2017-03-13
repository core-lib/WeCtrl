package org.qfox.wectrl.service.bean.weixin;

import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.weixin.UserDAO;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.qfox.wectrl.service.weixin.UserService;
import org.springframework.stereotype.Service;

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

        return null;
    }

}
