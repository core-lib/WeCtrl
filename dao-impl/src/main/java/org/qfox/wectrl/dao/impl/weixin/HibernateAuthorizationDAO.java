package org.qfox.wectrl.dao.impl.weixin;

import org.qfox.wectrl.core.weixin.Authorization;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.qfox.wectrl.dao.weixin.AuthorizationDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchangpei on 17/3/13.
 */
@Repository
public class HibernateAuthorizationDAO extends HibernateGenericDAO<Authorization, Long> implements AuthorizationDAO {
}
