package org.qfox.wectrl.dao.impl.weixin;

import org.qfox.wectrl.core.weixin.Token;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.qfox.wectrl.dao.weixin.TokenDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by payne on 2017/3/5.
 */
@Repository
public class HibernateTokenDAO extends HibernateGenericDAO<Token, Long> implements TokenDAO {
}
