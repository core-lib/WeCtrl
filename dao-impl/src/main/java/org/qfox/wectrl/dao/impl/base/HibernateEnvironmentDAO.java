package org.qfox.wectrl.dao.impl.base;

import org.qfox.wectrl.core.base.Environment;
import org.qfox.wectrl.dao.base.EnvironmentDAO;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Repository
public class HibernateEnvironmentDAO extends HibernateGenericDAO<Environment, Long> implements EnvironmentDAO {
}
