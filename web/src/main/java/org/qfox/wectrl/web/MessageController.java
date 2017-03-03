package org.qfox.wectrl.web;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.POST;
import org.qfox.jestful.core.annotation.Query;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangchangpei on 17/3/1.
 */
@Jestful("/message")
@Controller
public class MessageController {

    @GET("/")
    public String validate(@Query("signature") String signature,
                           @Query("timestamp") String timestamp,
                           @Query("nonce") String nonce,
                           @Query("echostr") String echostr,
                           HttpServletRequest request) {


        return "@:" + echostr;
    }

    @POST("/")
    public String receive() {

        return "@:";
    }

}
