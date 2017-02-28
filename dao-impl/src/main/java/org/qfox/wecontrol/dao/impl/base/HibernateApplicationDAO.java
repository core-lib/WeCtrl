package org.qfox.wecontrol.dao.impl.base;

import org.qfox.wecontrol.core.base.Application;
import org.qfox.wecontrol.dao.base.ApplicationDAO;
import org.qfox.wecontrol.dao.impl.HibernateGenericDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchangpei on 17/2/28.
 */
@Repository
public class HibernateApplicationDAO extends HibernateGenericDAO<Application, Long> implements ApplicationDAO {
}
