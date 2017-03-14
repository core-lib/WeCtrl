package org.qfox.wectrl.web.mch;

import org.qfox.jestful.client.Client;
import org.qfox.jestful.core.annotation.*;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.common.Randoms;
import org.qfox.wectrl.common.weixin.aes.AesException;
import org.qfox.wectrl.common.weixin.aes.SHA1;
import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.base.Environment;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.base.EnvironmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Jestful("/applications/{appID:\\w+}/environments")
@Controller
public class EnvironmentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
                           @Body("domain") String domain,
                           @Body("pushURL") String pushURL,
                           @Body("acquiescent") boolean acquiescent) throws UnsupportedEncodingException, AesException, MalformedURLException {
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
        if (StringUtils.isEmpty(domain)) {
            errors.add("网页授权回调域名不能为空");
        } else if (!domain.matches("^http(s)?://[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*(:\\d+)?$")) {
            errors.add("网页授权回调域名格式不正确");
        }
        if (StringUtils.isEmpty(pushURL)) {
            errors.add("消息推送URL不能为空");
        }
        // TODO 有可能被指向到自己造成死递归!!!!!!!!!
        else if (!pushURL.matches("^http(s)?://[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*(:\\d+)?/[^?#]+$")) {
            errors.add("消息推送URL格式不正确 请填写完整的网页地址 且不能包含'?' 和 '#'");
        }
        if (environmentServiceBean.isApplicationEnvKeyExisted(appID, envKey)) {
            errors.add("环境Key已存在");
        }
        Application application = applicationServiceBean.getApplicationByAppID(appID);
        if (application == null) {
            errors.add("AppID不存在");
        }

        if (!errors.isEmpty()) {
            return new JsonResult(false, "FAIL", errors);
        }

        JsonResult verification = verify(pushURL, application.getToken());
        if (!verification.isSuccess()) {
            return verification;
        }

        Environment environment = new Environment();
        environment.setEnvName(envName);
        environment.setEnvKey(envKey);
        environment.setDomain(domain);
        environment.setPushURL(pushURL);
        environment.setAcquiescent(acquiescent);
        environment.setApplication(new App(application));

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

    @PUT(value = "/{envKey:\\w+}", produces = "application/json")
    public JsonResult update(@Path("appID") String appID,
                             @Path("envKey") String envKey,
                             @Body("envName") String envName,
                             @Body("domain") String domain,
                             @Body("pushURL") String pushURL,
                             @Body("acquiescent") boolean acquiescent) throws UnsupportedEncodingException, AesException, MalformedURLException {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isEmpty(envName)) {
            errors.add("环境名称不能为空");
        }
        if ("new".equalsIgnoreCase(envKey)) {
            errors.add("环境Key不能为new");
        }
        if (StringUtils.isEmpty(domain)) {
            errors.add("网页授权回调域名不能为空");
        } else if (!domain.matches("^http(s)?://[a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*$")) {
            errors.add("网页授权回调域名格式不正确");
        }
        if (StringUtils.isEmpty(pushURL)) {
            errors.add("消息推送URL不能为空");
        }
        // TODO 有可能被指向到自己造成死递归!!!!!!!!!
        else if (!pushURL.matches("^http(s)?://[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*(:\\d+)?/[^?#]+$")) {
            errors.add("消息推送URL格式不正确 请填写完整的网页地址 且不能包含'?' 和 '#'");
        }
        Application application = applicationServiceBean.getApplicationByAppID(appID);
        if (application == null) {
            errors.add("AppID不存在");
        }

        if (!errors.isEmpty()) {
            return new JsonResult(false, "FAIL", errors);
        }

        JsonResult verification = verify(pushURL, application.getToken());
        if (!verification.isSuccess()) {
            return verification;
        }

        Environment env = new Environment();
        env.setEnvName(envName);
        env.setEnvKey(envKey);
        env.setDomain(domain);
        env.setPushURL(pushURL);
        env.setAcquiescent(acquiescent);
        env.setApplication(new App(application));

        environmentServiceBean.update(env);

        return new JsonResult("/applications/" + appID + "/environments/" + envKey);
    }

    private JsonResult verify(String pushURL, String token) {
        try {
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
            String nonce = Randoms.number(9);
            String echostr = Randoms.number(18);
            String signature = SHA1.sign(token, timestamp, nonce);

            Client client = Client.builder().setEndpoint(new URL(pushURL)).build();
            VerificationAPI api = client.create(VerificationAPI.class);
            String result = api.get(signature, timestamp, nonce, echostr);
            if (!echostr.equals(result)) {
                return new JsonResult(false, "Fail", "消息推送URL验证失败");
            }

            return JsonResult.OK;
        } catch (Exception e) {
            logger.error("消息推送URL验证失败:{}", e);
            return new JsonResult(false, "Fail", "消息推送URL验证失败");
        }
    }

    @DELETE(value = "/{envKey:\\w+}", produces = "application/json")
    public JsonResult delete(@Path("appID") String appID, @Path("envKey") String envKey) {
        environmentServiceBean.deleteByAppIDAndEnvKey(appID, envKey);
        return JsonResult.OK;
    }

    @GET("/{envKey:\\w+}/acquiescence")
    public JsonResult acquiescence(@Path("appID") String appID, @Path("envKey") String envKey) {
        Environment env = environmentServiceBean.getApplicationEnvironment(appID, envKey);
        if (env == null) {
            new JsonResult(false, "Not Found", "应用环境不存在");
        }
        return new JsonResult(true, "OK", env.isAcquiescent());
    }

}
