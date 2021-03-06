package org.qfox.wectrl.service.weixin;

import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.service.GenericService;

/**
 * Created by payne on 2017/3/6.
 */
public interface UserService extends GenericService<User, Long> {

    Page<User> getPagedApplicationUsers(String appID, int pagination, int capacity, String keyword);

    User get(String appID, String openID, String... fetchs);

    int setUserToEnvironment(String appID, String openID, String envKey);

    User getUserWithEnvironment(String appID, String openID);

}
