package org.qfox.wectrl.web.mch;

import org.apache.commons.codec.binary.Hex;
import org.qfox.jestful.core.annotation.*;
import org.qfox.jestful.server.annotation.Session;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Jestful("/applications")
@Controller
public class ApplicationController {

    @Resource
    private ApplicationService applicationServiceBean;

    @GET("/{appID:\\w+}/index")
    public String index(@Path("appID") String appID, HttpServletRequest request) {
        Application app = applicationServiceBean.getApplicationByAppID(appID);
        request.setAttribute("app", app);
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
                           @Body("originalID") String originalID) throws NoSuchAlgorithmException {

        List<String> errors = new ArrayList<>();
        if (StringUtils.isEmpty(appID)) {
            errors.add("App ID 不能为空");
        }
        if ("new".equalsIgnoreCase(appID)) {
            errors.add("App ID 不能为new");
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
        app.setPushURL("https://" + appID + ".wujiexiankeji.com/message");
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
        // 生成中控服务器的应用密钥
        String uuid = UUID.randomUUID().toString();
        byte[] md5 = MessageDigest.getInstance("md5").digest(uuid.getBytes());
        String hex = Hex.encodeHexString(md5);
        app.setSecret(hex);

        applicationServiceBean.merge(app);

        return new JsonResult("/applications/" + appID + "/index");
    }

    @GET("/{appID:(?!new)\\w+}")
    public String edit(@Path("appID") String appID, HttpServletRequest request) {
        Application application = applicationServiceBean.getApplicationByAppID(appID);
        request.setAttribute("app", application);

        return "forward:/view/base/application/edit.jsp";
    }

    @PUT(value = "/{appID:\\w+}", produces = "application/json")
    public JsonResult update(@Path("appID") String appID,
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
        if (StringUtils.isEmpty(appSecret)) {
            errors.add("App Secret 不能为空");
        }
        if ("new".equalsIgnoreCase(appID)) {
            errors.add("App ID 不能为new");
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

        applicationServiceBean.merge(app, "appSecret", "pushURL", "token", "encoding", "portraitURL", "QRCodeURL", "appName", "appNumber", "type");

        return new JsonResult("/applications/" + appID + "/index");
    }

    @DELETE(value = "/{appID:\\w+}", produces = "application/json")
    public JsonResult delete(@Path("appID") String appID) {
        Application app = applicationServiceBean.getApplicationByAppID(appID);
        applicationServiceBean.delete(app);
        return JsonResult.OK;
    }

    @POST(value = "/{appID:\\w+}/secret", produces = "application/json")
    public JsonResult secret(@Path("appID") String appID) throws NoSuchAlgorithmException {
        Application app = applicationServiceBean.getApplicationByAppID(appID);
        // 生成中控服务器的应用密钥
        String uuid = UUID.randomUUID().toString();
        byte[] md5 = MessageDigest.getInstance("md5").digest(uuid.getBytes());
        String hex = Hex.encodeHexString(md5);
        app.setSecret(hex);
        int count = applicationServiceBean.merge(app, "secret");
        return count == 2 ? new JsonResult(hex) : JsonResult.FAIL;
    }
}
