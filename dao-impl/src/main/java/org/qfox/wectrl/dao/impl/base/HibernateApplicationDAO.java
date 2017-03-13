package org.qfox.wectrl.dao.impl.base;

import org.hibernate.SQLQuery;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.dao.base.ApplicationDAO;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchangpei on 17/2/28.
 */
@Repository
public class HibernateApplicationDAO extends HibernateGenericDAO<Application, Long> implements ApplicationDAO {

    @Override
    public boolean startPulling(String appID) {
        StringBuilder SQL = new StringBuilder();
        SQL.append(" UPDATE");
        SQL.append("     base_application_tbl");
        SQL.append(" SET");
        SQL.append("     pulling = TRUE");
        SQL.append(" WHERE");
        SQL.append("     appID = :appID");
        SQL.append(" AND");
        SQL.append("     pulling = FALSE");

        SQLQuery query = currentSession().createSQLQuery(SQL.toString());
        query.setParameter("appID", appID);

        int count = query.executeUpdate();

        return count == 1;
    }

    @Override
    public boolean endPulling(String appID) {
        StringBuilder SQL = new StringBuilder();
        SQL.append(" UPDATE");
        SQL.append("     base_application_tbl");
        SQL.append(" SET");
        SQL.append("     pulling = FALSE");
        SQL.append(" WHERE");
        SQL.append("     appID = :appID");
        SQL.append(" AND");
        SQL.append("     pulling = TRUE");

        SQLQuery query = currentSession().createSQLQuery(SQL.toString());
        query.setParameter("appID", appID);

        int count = query.executeUpdate();

        return count == 1;
    }

    @Override
    public int updateToVerified(String appID) {
        StringBuilder SQL = new StringBuilder();
        SQL.append(" UPDATE");
        SQL.append("     base_application_tbl");
        SQL.append(" SET");
        SQL.append("     verified = TRUE,");
        SQL.append("     dateVerified = now()");
        SQL.append(" WHERE");
        SQL.append("     appID = :appID");

        SQLQuery query = currentSession().createSQLQuery(SQL.toString());
        query.setParameter("appID", appID);

        return query.executeUpdate();
    }

}
