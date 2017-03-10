package org.qfox.wectrl.dao.impl.weixin;

import org.hibernate.SQLQuery;
import org.qfox.wectrl.core.weixin.message.Message;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.qfox.wectrl.dao.weixin.WeixinMessageDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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

}
