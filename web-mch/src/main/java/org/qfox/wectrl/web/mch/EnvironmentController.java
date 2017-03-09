package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.*;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.base.Environment;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.base.EnvironmentService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Jestful("/applications/{appID:\\w+}/environments")
@Controller
public class EnvironmentController {

    @Resource
    private ApplicationService applicationServiceBean;

    @Resource
    private EnvironmentService environmentServiceBean;

    @GET("/")
    public String index(@Path("appID") String appID,
                        @Query("pagination") int pagination,
                        @Query("capacity") int capacity,
                        HttpServletRequest request) {

        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Application app = applicationServiceBean.getApplicationByAppID(appID);
        request.setAttribute("app", app);

        Page<Environment> page = environmentServiceBean.getPagedApplicationEnvironments(appID, pagination, capacity);
        request.setAttribute("page", page);

        return "forward:/view/base/environment/index.jsp";
    }

    @POST(value = "/", produces = "application/json")
    public JsonResult save(@Path("appID") String appID,
                           @Body("envName") String envName,
                           @Body("envKey") String envKey,
                           @Body("authorizeURL") String authorizeURL,
                           @Body("pushURL") String pushURL,
                           @Body("acquiescent") boolean acquiescent) {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isEmpty(envName)) {
            errors.add("环境名称不能为空");
        }
        if (StringUtils.isEmpty(envKey)) {
            errors.add("环境Key不能为空");
        }
        if ("new".equalsIgnoreCase(envKey)) {
            errors.add("环境Key不能为new");
        }
        if (StringUtils.isEmpty(authorizeURL)) {
            errors.add("网页授权URL不能为空");
        }
        if (StringUtils.isEmpty(pushURL)) {
            errors.add("消息推送URL不能为空");
        }
        if (environmentServiceBean.isApplicationEnvKeyExisted(appID, envKey)) {
            errors.add("环境Key已存在");
        }

        if (!errors.isEmpty()) {
            return new JsonResult(false, "FAIL", errors);
        }

        Environment environment = new Environment();
        environment.setEnvName(envName);
        environment.setEnvKey(envKey);
        environment.setAuthorizeURL(authorizeURL);
        environment.setPushURL(pushURL);
        environment.setAcquiescent(acquiescent);

        Application application = applicationServiceBean.getApplicationByAppID(appID);
        App app = new App(application);
        environment.setApplication(app);

        environmentServiceBean.save(environment);

        return new JsonResult("/applications/" + appID + "/environments");
    }

    @GET("/{envKey:\\w+}")
    public String edit(@Path("appID") String appID,
                       @Path("envKey") String envKey,
                       HttpServletRequest request) {
        Application application = applicationServiceBean.getApplicationByAppID(appID);
        request.setAttribute("app", application);

        Environment environment = environmentServiceBean.getApplicationEnvironment(appID, envKey);
        request.setAttribute("env", environment);
        return "forward:/view/base/environment/edit.jsp";
    }

    @PUT(value = "/{oldEnvKey:\\w+}", produces = "application/json")
    public JsonResult update(@Path("appID") String appID,
                             @Path("oldEnvKey") String oldEnvKey,
                             @Body("envName") String envName,
                             @Body("envKey") String newEnvKey,
                             @Body("authorizeURL") String authorizeURL,
                             @Body("pushURL") String pushURL,
                             @Body("acquiescent") boolean acquiescent) {
        Environment env = environmentServiceBean.getApplicationEnvironment(appID, oldEnvKey);

        List<String> errors = new ArrayList<>();
        if (StringUtils.isEmpty(envName)) {
            errors.add("环境名称不能为空");
        }
        if (StringUtils.isEmpty(newEnvKey)) {
            errors.add("环境Key不能为空");
        }
        if ("new".equalsIgnoreCase(newEnvKey)) {
            errors.add("环境Key不能为new");
        }
        if (StringUtils.isEmpty(authorizeURL)) {
            errors.add("网页授权URL不能为空");
        }
        if (StringUtils.isEmpty(pushURL)) {
            errors.add("消息推送URL不能为空");
        }
        if (!env.getEnvKey().equals(newEnvKey) && environmentServiceBean.isApplicationEnvKeyExisted(appID, newEnvKey)) {
            errors.add("环境Key已存在");
        }

        if (!errors.isEmpty()) {
            return new JsonResult(false, "FAIL", errors);
        }

        env.setEnvName(envName);
        env.setEnvKey(newEnvKey);
        env.setAuthorizeURL(authorizeURL);
        env.setPushURL(pushURL);
        env.setAcquiescent(acquiescent);

        environmentServiceBean.update(env);

        return new JsonResult("/applications/" + appID + "/environments/" + newEnvKey);
    }

    @DELETE("/{envKey:\\w+}")
    public JsonResult delete(@Path("appID") String appID, @Path("envKey") String envKey) {
        Environment env = environmentServiceBean.getApplicationEnvironment(appID, envKey);
        environmentServiceBean.delete(env);
        return JsonResult.OK;
    }

}
