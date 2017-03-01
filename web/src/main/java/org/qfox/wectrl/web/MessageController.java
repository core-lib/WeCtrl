package org.qfox.wectrl.web;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.POST;
import org.qfox.jestful.core.annotation.Query;
import org.springframework.stereotype.Controller;

/**
 * Created by yangchangpei on 17/3/1.
 */
@Jestful("/message")
@Controller
public class MessageController {

    @GET("/")
    public String validate(@Query("echostr") String echostr) {
        return "@:" + echostr;
    }

    @POST("/")
    public String receive() {

        return "@:";
    }

}
