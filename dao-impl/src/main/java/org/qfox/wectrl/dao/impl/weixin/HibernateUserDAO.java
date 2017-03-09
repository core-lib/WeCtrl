package org.qfox.wectrl.dao.impl.weixin;

import org.hibernate.SQLQuery;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.qfox.wectrl.dao.weixin.UserDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by payne on 2017/3/6.
 */
@Repository
public class HibernateUserDAO extends HibernateGenericDAO<User, Long> implements UserDAO {
    @Override
    public int merge(User user) {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO weixin_user_tbl (");
        sql.append("     dateCreated,");
        sql.append("     lastUpdated,");
        sql.append("     version,");
        sql.append("     deleted,");
        sql.append("     application_id,");
        sql.append("     application_appID,");
        sql.append("     application_appName,");
        sql.append("     subscribed,");
        sql.append("     openID,");
        sql.append("     nickname,");
        sql.append("     gender,");
        sql.append("     `language`,");
        sql.append("     city,");
        sql.append("     province,");
        sql.append("     country,");
        sql.append("     portraitURL,");
        sql.append("     dateSubscribed,");
        sql.append("     unionID,");
        sql.append("     remark,");
        sql.append("     groupID");
        sql.append(" )");
        sql.append(" VALUES (");
        sql.append("         now(),");
        sql.append("         now(),");
        sql.append("         0,");
        sql.append("         0,");
        sql.append("         :applicationId,");
        sql.append("         :applicationAppID,");
        sql.append("         :applicationAppName,");
        sql.append("         :subscribed,");
        sql.append("         :openID,");
        sql.append("         :nickname,");
        sql.append("         :gender,");
        sql.append("         :language,");
        sql.append("         :city,");
        sql.append("         :province,");
        sql.append("         :country,");
        sql.append("         :portraitURL,");
        sql.append("         :dateSubscribed,");
        sql.append("         :unionID,");
        sql.append("         :remark,");
        sql.append("         :groupID");
        sql.append("     ) ON DUPLICATE KEY UPDATE subscribed = :subscribed, dateSubscribed = :dateSubscribed");

        SQLQuery query = currentSession().createSQLQuery(sql.toString());

        query.setParameter("applicationId", user.getApplication().getId());
        query.setParameter("applicationAppID", user.getApplication().getAppID());


        return 0;
    }
}
