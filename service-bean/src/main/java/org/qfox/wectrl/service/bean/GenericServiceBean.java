package org.qfox.wectrl.service.bean;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.service.GenericService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by payne on 2017/1/30.
 */
public abstract class GenericServiceBean<T extends Serializable, PK extends Serializable> implements GenericService<T, PK> {

    protected abstract GenericDAO<T, PK> getEntityDAO();

    @Transactional
    @Override
    public void save(T entity) {
        getEntityDAO().save(entity);
    }

    @Transactional
    @Override
    public void delete(T entity) {
        getEntityDAO().delete(entity);
    }

    @Transactional
    @Override
    public T deleteByID(PK id) {
        T entity = get(id);
        if (entity != null) {
            delete(entity);
        }
        return entity;
    }

    @Transactional
    @Override
    public void update(T entity) {
        getEntityDAO().update(entity);
    }

    @Override
    public T get(PK id, String... fetchs) {
        if (fetchs.length > 0) {
            Criteria criteria = getEntityDAO().createCriteria();
            criteria.add(Restrictions.eq("id", id));
            for (String fetch : fetchs) {
                criteria.setFetchMode(fetch, FetchMode.JOIN);
            }
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return (T) criteria.uniqueResult();
        }
        return getEntityDAO().get(id);
    }

    @Override
    public T load(PK id) {
        return getEntityDAO().load(id);
    }

    @Override
    public List<T> listAll() {
        return getEntityDAO().loadAll();
    }

    @Override
    public void evict(T entity) {
        getEntityDAO().evict(entity);
    }

    @Transactional
    @Override
    public int merge(T entity, String... properties) {
        return getEntityDAO().merge(entity, properties);
    }
}
