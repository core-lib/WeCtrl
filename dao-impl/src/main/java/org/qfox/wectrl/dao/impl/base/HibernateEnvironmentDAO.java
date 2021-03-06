package org.qfox.wectrl.dao.impl.base;

import org.hibernate.SQLQuery;
import org.qfox.wectrl.core.base.Environment;
import org.qfox.wectrl.dao.base.EnvironmentDAO;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Repository
public class HibernateEnvironmentDAO extends HibernateGenericDAO<Environment, Long> implements EnvironmentDAO {

    @Override
    public int updateToVerified(String appID, String envKey) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE");
        sql.append("     base_environment_tbl");
        sql.append(" SET");
        sql.append("     verified = TRUE,");
        sql.append("     dateVerified = now()");
        sql.append(" WHERE");
        sql.append("     application_appID = :appID");
        sql.append(" AND envKey = :envKey");

        SQLQuery query = currentSession().createSQLQuery(sql.toString());
        query.setParameter("appID", appID);
        query.setParameter("envKey", envKey);

        return query.executeUpdate();
    }

    @Override
    public int updateToNormal(String appID) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE");
        sql.append("    base_environment_tbl");
        sql.append(" SET");
        sql.append("    acquiescent = FALSE");
        sql.append(" WHERE");
        sql.append("    application_appID = :appID");

        SQLQuery query = currentSession().createSQLQuery(sql.toString());
        query.setParameter("appID", appID);

        return query.executeUpdate();
    }

    @Override
    public int deleteByAppIDAndEnvKey(String appID, String envKey) {
        String sql = "DELETE FROM base_environment_tbl WHERE application_appID = :appID AND envKey = :envKey";
        SQLQuery query = currentSession().createSQLQuery(sql);
        query.setParameter("appID", appID);
        query.setParameter("envKey", envKey);
        return query.executeUpdate();
    }

}
