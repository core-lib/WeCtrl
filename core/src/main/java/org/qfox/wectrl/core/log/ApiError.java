package org.qfox.wectrl.core.log;

import org.qfox.wectrl.core.Domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yangchangpei on 17/3/15.
 */
@Entity
@Table(name = "log_api_error_tbl")
public class ApiError extends Domain {
    private static final long serialVersionUID = 5145929624457984320L;

    private Status status;
    private String header;
    private String traces;



}
