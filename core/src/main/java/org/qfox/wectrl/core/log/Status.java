package org.qfox.wectrl.core.log;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by yangchangpei on 17/3/15.
 */
@Embeddable
public class Status implements Serializable {
    private static final long serialVersionUID = 4696837565544917589L;

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
