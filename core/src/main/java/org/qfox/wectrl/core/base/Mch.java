package org.qfox.wectrl.core.base;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Embeddable
public class Mch implements Serializable {
    private static final long serialVersionUID = -3655250362471333411L;

    private Long id;
    private String name;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
