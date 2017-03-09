package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.*;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.transaction.SessionProvider;
import org.qfox.wectrl.service.weixin.TokenService;
import org.qfox.wectrl.service.weixin.UserService;
import org.qfox.wectrl.service.weixin.pulling.PullTask;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Executors;

/**
 * Created by payne on 2017/3/6.
 */
@Jestful("/applications/{appID:\\w+}/users")
@Controller
public class UserController {

    @Resource
    private ApplicationService applicationServiceBean;

    @Resource
    private TokenService tokenServiceBean;

    @Resource
    private SessionProvider defaultSessionProvider;

    @Resource
    private UserService userServiceBean;

    @GET("/")
    public String index(@Path("appID") String appID,
                        @Query("pagination") int pagination,
                        @Query("capacity") int capacity,
                        HttpServletRequest request) {

        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Application app = applicationServiceBean.getApplicationByAppID(appID);
        request.setAttribute("app", app);

        Page<User> page = userServiceBean.getPagedApplicationUsers(appID, pagination, capacity);
        request.setAttribute("page", page);

        return "forward:/view/weixin/user/index.jsp";
    }

    @POST("/")
    public JsonResult refresh(@Path("appID") String appID) {
        Application application = applicationServiceBean.getApplicationByAppID(appID);

        if (application != null) {
            boolean success = applicationServiceBean.startPulling(appID);
            if (success) {
                Executors.newFixedThreadPool(1).submit(new PullTask(application, tokenServiceBean, defaultSessionProvider, userServiceBean));
            } else {
                return new JsonResult(false, "FAIL", "已启动");
            }
        } else {
            return new JsonResult(false, "FAIL", "appID 不存在");
        }

        return new JsonResult(null);
    }

}
