package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.*;
import org.qfox.jestful.server.annotation.Session;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.common.base.ApplicationType;
import org.qfox.wectrl.common.base.EncodingMode;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.base.Encoding;
import org.qfox.wectrl.core.base.Mch;
import org.qfox.wectrl.core.base.Merchant;
import org.qfox.wectrl.service.base.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Jestful("/applications")
@Controller
public class ApplicationController {

    @Resource
    private ApplicationService applicationServiceBean;

    @GET("/")
    public String index(@Session(SessionKey.MERCHANT) Merchant merchant,
                        @Query("pagination") int pagination,
                        @Query("capacity") int capacity,
                        HttpServletRequest request) {
        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Long merchantId = merchant.getId();
        Page<Application> page = applicationServiceBean.getPagedMerchantApplications(merchantId, pagination, capacity);
        request.setAttribute("page", page);
        return "forward:/view/base/application/index.jsp";
    }

    @GET("/new")
    public String create() {
        return "forward:/view/base/application/create.jsp";
    }

    @POST(value = "/", produces = "application/json")
    public JsonResult save(@Session(SessionKey.MERCHANT) Merchant merchant,
                           @Body("appID") String appID,
                           @Body("appSecret") String appSecret,
                           @Body("token") String token,
                           @Body("mode") EncodingMode mode,
                           @Body("password") String password,
                           @Body("portraitURL") String portraitURL,
                           @Body("QRCodeURL") String QRCodeURL,
                           @Body("appName") String appName,
                           @Body("appNumber") String appNumber,
                           @Body("type") ApplicationType type,
                           @Body("originalID") String originalID) {

        List<String> errors = new ArrayList<>();
        if (StringUtils.isEmpty(appID)) {
            errors.add("App ID 不能为空");
        }
        if (StringUtils.isEmpty(appSecret)) {
            errors.add("App Secret 不能为空");
        }
        if (StringUtils.isEmpty(token)) {
            errors.add("Token 不能为空");
        }
        if (mode == null) {
            errors.add("请选择消息加解密方式");
        }
        if (mode != EncodingMode.PLAIN && StringUtils.isEmpty(password)) {
            errors.add("非明文模式下EncodingAESKey不能为空");
        }
        if (type == null) {
            errors.add("请选择公众号类型");
        }
        if (StringUtils.isEmpty(originalID)) {
            errors.add("原始ID 不能为空");
        }

        if (applicationServiceBean.isAppIDExisted(appID)) {
            errors.add("App ID 已存在");
        }
        if (applicationServiceBean.isOriginalIDExisted(originalID)) {
            errors.add("原始ID已存在");
        }

        if (!errors.isEmpty()) {
            return new JsonResult(false, "FAIL", errors);
        }

        Application app = new Application();
        app.setAppID(appID);
        app.setAppSecret(appSecret);
        app.setPushURL("https://" + appID + ".wectrl.com/message");
        app.setToken(token);

        Encoding encoding = new Encoding();
        encoding.setMode(mode);
        encoding.setPassword(password);
        app.setEncoding(encoding);

        app.setPortraitURL(portraitURL);
        app.setQRCodeURL(QRCodeURL);
        app.setAppName(appName);
        app.setAppNumber(appNumber);
        app.setType(type);
        app.setOriginalID(originalID);

        Mch mch = new Mch(merchant);
        app.setMerchant(mch);
        applicationServiceBean.save(app);

        return new JsonResult("/applications");
    }

    @GET("/{appID:(?!new)\\w+}")
    public String edit(@Path("appID") String appID, HttpServletRequest request) {
        Application application = applicationServiceBean.getApplicationByAppID(appID);
        request.setAttribute("app", application);

        return "forward:/view/base/application/edit.jsp";
    }

    @PUT(value = "/{oldAppID:(?!new)\\w+}", produces = "application/json")
    public JsonResult update(@Path("oldAppID") String oldAppID,
                             @Body("appID") String newAppID,
                             @Body("appSecret") String appSecret,
                             @Body("token") String token,
                             @Body("mode") EncodingMode mode,
                             @Body("password") String password,
                             @Body("portraitURL") String portraitURL,
                             @Body("QRCodeURL") String QRCodeURL,
                             @Body("appName") String appName,
                             @Body("appNumber") String appNumber,
                             @Body("type") ApplicationType type,
                             @Body("originalID") String originalID) {

        Application app = applicationServiceBean.getApplicationByAppID(oldAppID);

        List<String> errors = new ArrayList<>();
        if (StringUtils.isEmpty(newAppID)) {
            errors.add("App ID 不能为空");
        }
        if (StringUtils.isEmpty(appSecret)) {
            errors.add("App Secret 不能为空");
        }
        if (StringUtils.isEmpty(token)) {
            errors.add("Token 不能为空");
        }
        if (mode == null) {
            errors.add("请选择消息加解密方式");
        }
        if (mode != EncodingMode.PLAIN && StringUtils.isEmpty(password)) {
            errors.add("非明文模式下EncodingAESKey不能为空");
        }
        if (type == null) {
            errors.add("请选择公众号类型");
        }
        if (StringUtils.isEmpty(originalID)) {
            errors.add("原始ID 不能为空");
        }

        if (!app.getAppID().equals(newAppID) && applicationServiceBean.isAppIDExisted(newAppID)) {
            errors.add("App ID 已存在");
        }
        if (!app.getOriginalID().equals(originalID) && applicationServiceBean.isOriginalIDExisted(originalID)) {
            errors.add("原始ID已存在");
        }

        if (!errors.isEmpty()) {
            return new JsonResult(false, "FAIL", errors);
        }

        app.setAppID(newAppID);
        app.setAppSecret(appSecret);
        app.setPushURL("https://" + newAppID + ".wectrl.com/message");
        app.setToken(token);

        Encoding encoding = new Encoding();
        encoding.setMode(mode);
        encoding.setPassword(password);
        app.setEncoding(encoding);

        app.setPortraitURL(portraitURL);
        app.setQRCodeURL(QRCodeURL);
        app.setAppName(appName);
        app.setAppNumber(appNumber);
        app.setType(type);
        app.setOriginalID(originalID);

        applicationServiceBean.update(app);

        return new JsonResult("/applications");
    }

    @DELETE("/{appID:(?!new)\\w+}")
    public JsonResult delete(@Path("appID") String appID) {
        Application app = applicationServiceBean.getApplicationByAppID(appID);
        applicationServiceBean.delete(app);
        return JsonResult.OK;
    }

}
