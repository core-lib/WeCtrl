package org.qfox.wectrl.dao.impl.base;

import org.qfox.wectrl.core.base.Merchant;
import org.qfox.wectrl.dao.base.MerchantDAO;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Repository
public class HibernateMerchantDAO extends HibernateGenericDAO<Merchant, Long> implements MerchantDAO {
}
