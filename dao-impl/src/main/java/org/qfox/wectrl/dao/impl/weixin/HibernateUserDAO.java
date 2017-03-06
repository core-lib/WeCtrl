package org.qfox.wectrl.dao.impl.weixin;

import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.qfox.wectrl.dao.weixin.UserDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by payne on 2017/3/6.
 */
@Repository
public class HibernateUserDAO extends HibernateGenericDAO<User, Long> implements UserDAO {
}
