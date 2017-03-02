package org.qfox.wectrl.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangchangpei on 17/3/2.
 */
public class Page<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = -3724044500222125800L;

    private List<T> entities = new ArrayList<>();
    private int pagination;
    private int capacity;
    private int total;

    public Page() {
    }

    public Page(int pagination, int capacity) {
        this.pagination = pagination;
        this.capacity = capacity;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public int getPagination() {
        return pagination;
    }

    public void setPagination(int pagination) {
        this.pagination = pagination;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
