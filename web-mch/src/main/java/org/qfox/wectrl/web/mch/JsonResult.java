package org.qfox.wectrl.web.mch;

import java.io.Serializable;

/**
 * Created by yangchangpei on 17/2/7.
 */
public class JsonResult implements Serializable {
    private static final long serialVersionUID = -1873046386569503428L;

    public static final JsonResult OK = new JsonResult(null);
    public static final JsonResult FAIL = new JsonResult(false, "FAIL", null);

    private boolean success;
    private String message;
    private Object entity;

    public JsonResult(Object entity) {
        this(true, "OK", entity);
    }

    public JsonResult(boolean success, String message, Object entity) {
        this.success = success;
        this.message = message;
        this.entity = entity;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
