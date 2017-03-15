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

    /**
     *
     * @param appID
     * @param openID
     * @return 如果appID不存在或者该app还没有设置默认的应用环境返回 null 否则一定包含 user.environment
     */
    User getUserWithEnvironment(String appID, String openID);

}
