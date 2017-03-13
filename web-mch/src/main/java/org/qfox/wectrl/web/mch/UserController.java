package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.*;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.base.Environment;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.base.EnvironmentService;
import org.qfox.wectrl.service.transaction.SessionProvider;
import org.qfox.wectrl.service.weixin.TokenService;
import org.qfox.wectrl.service.weixin.UserService;
import org.qfox.wectrl.service.weixin.pulling.PullTask;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    @Resource
    private EnvironmentService environmentServiceBean;

    @GET("/")
    public String index(@Path("appID") String appID,
                        @Query("pagination") int pagination,
                        @Query("capacity") int capacity,
                        @Query("keyword") String keyword,
                        HttpServletRequest request) {

        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Application app = applicationServiceBean.getApplicationByAppID(appID);
        request.setAttribute("app", app);

        List<Environment> environments = environmentServiceBean.listAll();
        request.setAttribute("environments", environments);

        Page<User> page = userServiceBean.getPagedApplicationUsers(appID, pagination, capacity, keyword);
        request.setAttribute("page", page);

        return "forward:/view/weixin/user/index.jsp";
    }

    @POST("/")
    public JsonResult refresh(@Path("appID") String appID) {
        Application application = applicationServiceBean.getApplicationByAppID(appID);

        if (application != null) {
            boolean success = applicationServiceBean.startPulling(appID);
            if (success) {
                Executors.newFixedThreadPool(1).submit(new PullTask(application, applicationServiceBean, tokenServiceBean, userServiceBean, defaultSessionProvider));
                return JsonResult.OK;
            } else {
                return new JsonResult(false, "FAIL", "已启动");
            }
        } else {
            return new JsonResult(false, "FAIL", "appID 不存在");
        }
    }

    @PUT("/{openID:[a-zA-Z0-9_-]+}")
    public JsonResult attach(@Path("appID") String appID,
                             @Path("openID") String openID,
                             @Body("envKey") String envKey) {
        int count = userServiceBean.setUserToEnvironment(appID, openID, envKey);
        return count > 0 ? JsonResult.OK : new JsonResult(false, "记录不存在或已经被更新", null);
    }

}
