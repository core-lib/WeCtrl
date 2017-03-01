package org.qfox.wectrl.web;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.POST;

/**
 * Created by yangchangpei on 17/3/1.
 */
@Jestful("/message")
public class MessageController {

    @GET("/")
    public String validate() {
        return "@:";
    }

    @POST("/")
    public String receive() {

        return "@:";
    }

}
