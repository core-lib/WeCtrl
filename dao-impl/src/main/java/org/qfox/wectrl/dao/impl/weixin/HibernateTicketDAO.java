package org.qfox.wectrl.dao.impl.weixin;

import org.qfox.wectrl.core.weixin.Ticket;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.qfox.wectrl.dao.weixin.TicketDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by payne on 2017/3/5.
 */
@Repository
public class HibernateTicketDAO extends HibernateGenericDAO<Ticket, Long> implements TicketDAO {
}
