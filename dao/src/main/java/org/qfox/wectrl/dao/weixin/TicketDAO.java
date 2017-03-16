package org.qfox.wectrl.dao.weixin;

import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.common.weixin.TicketType;
import org.qfox.wectrl.core.weixin.Ticket;
import org.qfox.wectrl.dao.GenericDAO;

/**
 * Created by payne on 2017/3/5.
 */
public interface TicketDAO extends GenericDAO<Ticket, Long> {

    Page<Ticket> getPagedApplicationTickets(String appID, TicketType type, int pagination, int capacity);

}
