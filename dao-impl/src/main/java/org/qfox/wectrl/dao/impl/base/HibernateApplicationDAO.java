package org.qfox.wectrl.dao.impl.base;

import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.dao.base.ApplicationDAO;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchangpei on 17/2/28.
 */
@Repository
public class HibernateApplicationDAO extends HibernateGenericDAO<Application, Long> implements ApplicationDAO {
}
