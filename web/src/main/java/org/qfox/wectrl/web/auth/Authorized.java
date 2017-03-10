package org.qfox.wectrl.web.auth;

import java.lang.annotation.*;

/**
 * Created by yangchangpei on 16/11/23.
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorized {

    boolean required() default true;

}
