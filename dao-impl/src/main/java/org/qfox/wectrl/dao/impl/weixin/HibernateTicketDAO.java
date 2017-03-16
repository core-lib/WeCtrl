package org.qfox.wectrl.dao.impl.weixin;

import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.common.weixin.TicketType;
import org.qfox.wectrl.core.weixin.Ticket;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.qfox.wectrl.dao.weixin.TicketDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by payne on 2017/3/5.
 */
@Repository
public class HibernateTicketDAO extends HibernateGenericDAO<Ticket, Long> implements TicketDAO {

    @Override
    public Page<Ticket> getPagedApplicationTickets(String appID, TicketType type, int pagination, int capacity) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT SQL_CALC_FOUND_ROWS");
        sql.append("     ticket.*");
        sql.append(" FROM");
        sql.append("     weixin_ticket_tbl AS ticket");
        sql.append(" INNER JOIN weixin_token_tbl AS token ON ticket.accessToken = token.`value`");
        sql.append(" WHERE");
        sql.append("     token.application_appID = :appID");
        sql.append(" AND ticket.type = :type");
        sql.append(" AND ticket.deleted = FALSE");
        sql.append(" ORDER BY");
        sql.append("     ticket.id DESC");

        SQLQuery query = currentSession().createSQLQuery(sql.toString());
        query.setParameter("appID", appID);
        query.setParameter("type", type.name());

        query.setFirstResult(pagination * capacity);
        query.setMaxResults(capacity);

        query.addEntity(Ticket.class);

        Page<Ticket> page = new Page<>(pagination, capacity);
        page.setEntities(query.list());
        page.setTotal((Integer) currentSession().createSQLQuery("SELECT FOUND_ROWS() AS total").addScalar("total", IntegerType.INSTANCE).uniqueResult());

        return page;
    }
}
