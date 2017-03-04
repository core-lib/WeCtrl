package org.qfox.wectrl.dao.impl.base;

import org.qfox.wectrl.core.base.Verification;
import org.qfox.wectrl.dao.base.VerificationDAO;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Repository
public class HibernateVerificationDAO extends HibernateGenericDAO<Verification, Long> implements VerificationDAO {
}
