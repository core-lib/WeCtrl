package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.springframework.stereotype.Controller;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Jestful("/")
@Controller
public class IndexController {

    @GET("/")
    public String index() {

        return "forward:/view/index.jsp";
    }

}
