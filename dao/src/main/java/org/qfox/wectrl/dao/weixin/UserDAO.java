package org.qfox.wectrl.dao.weixin;

import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.dao.GenericDAO;

/**
 * Created by payne on 2017/3/6.
 */
public interface UserDAO extends GenericDAO<User, Long> {

    int merge(User user);

    Page<User> getPagedApplicationUsers(String appID, int pagination, int capacity, String keyword);

    int setUserToEnvironment(String appID, String openID, String envKey);

}
