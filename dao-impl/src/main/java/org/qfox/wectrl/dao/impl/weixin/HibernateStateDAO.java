package org.qfox.wectrl.dao.impl.weixin;

import org.qfox.wectrl.core.weixin.State;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.qfox.wectrl.dao.weixin.StateDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchangpei on 17/3/13.
 */
@Repository
public class HibernateStateDAO extends HibernateGenericDAO<State, Long> implements StateDAO {
}
