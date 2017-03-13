package org.qfox.wectrl.dao.impl.weixin;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Env;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.qfox.wectrl.dao.weixin.UserDAO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Override
    public Page<User> getPagedApplicationUsers(String appID, int pagination, int capacity, String keyword) {
        Page<User> page = new Page<>(pagination, capacity);

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT SQL_CALC_FOUND_ROWS");
        sql.append("     u.id,");
        sql.append("     u.portraitURL,");
        sql.append("     u.nickname,");
        sql.append("     u.openID,");
        sql.append("     u.subscribed,");
        sql.append("     u.dateSubscribed,");
        sql.append("     e.id AS envId");
        sql.append("     e.envKey,");
        sql.append("     e.envName");
        sql.append(" FROM");
        sql.append("     weixin_user_tbl AS u");
        sql.append(" LEFT JOIN base_environment_tbl AS e ON u.environment_id = e.id");
        sql.append(" WHERE");
        sql.append("     u.application_appID = :appID");
        sql.append(" AND u.deleted = FALSE");
        if (!StringUtils.isEmpty(keyword)) {
            sql.append(" AND u.nickname LIKE :keyword");
        }
        sql.append(" ORDER BY");
        sql.append("     u.id DESC");

        SQLQuery query = currentSession().createSQLQuery(sql.toString());
        query.setParameter("appID", appID);
        if (!StringUtils.isEmpty(keyword)) {
            query.setParameter("keyword", "%" + keyword + "%");
        }

        query.setFirstResult(pagination * capacity);
        query.setMaxResults(capacity);

        query.addScalar("id", LongType.INSTANCE);
        query.addScalar("portraitURL", StringType.INSTANCE);
        query.addScalar("nickname", StringType.INSTANCE);
        query.addScalar("openID", StringType.INSTANCE);
        query.addScalar("subscribed", BooleanType.INSTANCE);
        query.addScalar("dateSubscribed", TimestampType.INSTANCE);
        query.addScalar("envId", LongType.INSTANCE);
        query.addScalar("envKey", StringType.INSTANCE);
        query.addScalar("envName", StringType.INSTANCE);

        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> maps = query.list();
        page.setTotal((Integer) currentSession().createSQLQuery("SELECT FOUND_ROWS() AS total").addScalar("total", IntegerType.INSTANCE).uniqueResult());

        for (Map<String, Object> map : maps) {
            User user = new User();
            user.setId((Long) map.get("id"));
            user.setPortraitURL((String) map.get("portraitURL"));
            user.setNickname((String) map.get("nickname"));
            user.setOpenID((String) map.get("openID"));
            user.setSubscribed((Boolean) map.get("subscribed"));
            user.setDateSubscribed((Date) map.get("dateSubscribed"));

            Long envId = (Long) map.get("envId");
            if (envId != null) {
                Env env = new Env();
                env.setId(envId);
                env.setEnvKey((String) map.get("envKey"));
                env.setEnvName((String) map.get("envName"));
                user.setEnvironment(env);
            }

            page.getEntities().add(user);
        }

        return page;
    }
}
