package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.Path;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.message.Image;
import org.qfox.wectrl.core.weixin.message.Text;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.weixin.WeixinMessageService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by payne on 2017/3/5.
 */
@Jestful("/applications/{appID:\\w+}/messages")
@Controller
public class MessageController {

    @Resource
    private ApplicationService applicationServiceBean;

    @Resource
    private WeixinMessageService weixinMessageServiceBean;

    @GET("/")
    public String index(@Path("appID") String appID, HttpServletRequest request) {
        Application app = applicationServiceBean.getApplicationByAppID(appID);
        request.setAttribute("app", app);
        return "forward:/view/weixin/message/index.jsp";
    }

    @GET(value = "/texts", produces = "application/json")
    public Page<Text> texts(@Path("appID") String appID,
                            @Query("pagination") int pagination,
                            @Query("capacity") int capacity,
                            @Query("keyword") String keyword) {

        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Page<Text> page = weixinMessageServiceBean.getPagedApplicationTexts(appID, pagination, capacity, keyword);

        return page;
    }

    @GET(value = "/images", produces = "application/json")
    public Page<Image> images(@Path("appID") String appID,
                              @Query("pagination") int pagination,
                              @Query("capacity") int capacity,
                              @Query("keyword") String keyword) {

        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Page<Image> page = weixinMessageServiceBean.getPagedApplicationImages(appID, pagination, capacity, keyword);

        return page;
    }

}
