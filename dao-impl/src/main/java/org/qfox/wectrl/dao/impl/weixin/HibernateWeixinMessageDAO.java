package org.qfox.wectrl.dao.impl.weixin;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.core.weixin.message.Message;
import org.qfox.wectrl.core.weixin.message.Text;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.qfox.wectrl.dao.weixin.WeixinMessageDAO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yangchangpei on 17/2/23.
 */
@Repository
public class HibernateWeixinMessageDAO extends HibernateGenericDAO<Message, Long> implements WeixinMessageDAO {

    @Override
    public int merge(Message message) throws Exception {
        StringBuilder sql = new StringBuilder();

        Table table = message.getClass().getAnnotation(Table.class);
        String tableName = table == null || "".equals(table.name()) ? message.getClass().getSimpleName() : table.name();
        sql.append(" INSERT INTO");
        sql.append("    `").append(tableName).append("`");
        sql.append(" (");
        int cols = 0;
        List<Object> values = new ArrayList<>();
        String idName = "id";
        PropertyDescriptor[] descriptors = Introspector.getBeanInfo(message.getClass()).getPropertyDescriptors();
        for (PropertyDescriptor descriptor : descriptors) {
            if ("class".equals(descriptor.getName())) {
                continue;
            }
            Method getter = descriptor.getReadMethod();
            if (getter.isAnnotationPresent(Transient.class)) {
                continue;
            }
            if (getter.isAnnotationPresent(Id.class)) {
                Column column = getter.getAnnotation(Column.class);
                idName = column == null || "".equals(column.name()) ? descriptor.getName() : column.name();
                continue;
            }
            if (cols > 0) {
                sql.append(", ");
            }
            Column column = getter.getAnnotation(Column.class);
            String columnName = column == null || "".equals(column.name()) ? descriptor.getName() : column.name();
            sql.append("`").append(columnName).append("`");

            Object value = getter.invoke(message);
            values.add(value);

            cols += 1;
        }
        sql.append(" )");
        sql.append(" VALUES (");
        for (int i = 0; i < cols; i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append("?");
        }
        sql.append(" ) ON DUPLICATE KEY UPDATE ").append("`").append(idName).append("`").append(" = ").append("`").append(idName).append("`");

        SQLQuery query = currentSession().createSQLQuery(sql.toString());
        for (int i = 0; i < cols; i++) {
            query.setParameter(i, values.get(i));
        }

        return query.executeUpdate();
    }

    @Override
    public Page<Text> getPagedApplicationTexts(String appID, int pagination, int capacity, String keyword) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT SQL_CALC_FOUND_ROWS");
        sql.append("      t.id textId,");
        sql.append("      t.dateCreated,");
        sql.append("      t.content,");
        sql.append("      t.sender,");
        sql.append("      t.appId,");
        sql.append("      u.id AS userId,");
        sql.append("      u.nickname,");
        sql.append("      u.portraitURL");
        sql.append("  FROM");
        sql.append("      weixin_text_tbl AS t");
        sql.append("  LEFT JOIN weixin_user_tbl AS u ON t.appId = u.application_appID");
        sql.append("  AND t.sender = u.openID");
        sql.append("  WHERE");
        sql.append("      t.appId = :appID");
        if (!StringUtils.isEmpty(keyword)) {
            sql.append("  AND (");
            sql.append("      t.content LIKE :keyword");
            sql.append("      OR u.nickname = :keyword");
            sql.append("  )");
        }
        sql.append(" ORDER BY");
        sql.append("    t.timeCreated DESC");

        SQLQuery query = currentSession().createSQLQuery(sql.toString());
        query.setParameter("appID", appID);
        if (!StringUtils.isEmpty(keyword)) {
            query.setParameter("keyword", "%" + keyword + "%");
        }

        query.setFirstResult(pagination * capacity);
        query.setMaxResults(capacity);

        query.addScalar("textId", LongType.INSTANCE);
        query.addScalar("dateCreated", TimestampType.INSTANCE);
        query.addScalar("content", StringType.INSTANCE);
        query.addScalar("sender", StringType.INSTANCE);
        query.addScalar("appId", StringType.INSTANCE);
        query.addScalar("userId", LongType.INSTANCE);
        query.addScalar("nickname", StringType.INSTANCE);
        query.addScalar("portraitURL", StringType.INSTANCE);

        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> items = query.list();

        List<Text> texts = new ArrayList<>();
        for (Map<String, Object> item : items) {
            Text text = new Text();
            text.setId((Long) item.get("textId"));
            text.setDateCreated((Date) item.get("dateCreated"));
            text.setContent((String) item.get("content"));
            text.setSender((String) item.get("sender"));
            text.setAppId((String) item.get("appId"));

            Long userId = (Long) item.get("userId");
            if (userId != null) {
                User user = new User();
                user.setId(userId);
                user.setNickname((String) item.get("nickname"));
                user.setPortraitURL((String) item.get("portraitURL"));
                text.setUser(user);
            }

            texts.add(text);
        }

        Page<Text> page = new Page(pagination, capacity);
        page.setEntities(texts);
        page.setTotal((Integer) currentSession().createSQLQuery("SELECT FOUND_ROWS() AS total").addScalar("total", IntegerType.INSTANCE).uniqueResult());

        return page;
    }

}
