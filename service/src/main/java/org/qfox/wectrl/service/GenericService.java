package org.qfox.wectrl.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by payne on 2017/1/30.
 */
public interface GenericService<T extends Serializable, PK extends Serializable> {

    void save(T entity);

    void delete(T entity);

    T deleteByID(PK id);

    void update(T entity);

    T get(PK id, String... fetchs);

    T load(PK id);

    List<T> listAll();

    void evict(T entity);

    int merge(T entity, String... properties);

}
