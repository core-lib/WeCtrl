package org.qfox.wectrl.web;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.POST;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.wectrl.common.base.EncodingMode;
import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.base.Verification;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.base.VerificationService;
import org.qfox.wectrl.web.aes.AesException;
import org.qfox.wectrl.web.aes.SHA1;
import org.qfox.wectrl.web.aes.WXBizMsgCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by yangchangpei on 17/3/1.
 */
@Jestful("/message")
@Controller
public class MessageController {

    @Resource
    private ApplicationService applicationServiceBean;

    @Resource
    private VerificationService verificationServiceBean;

    @GET("/")
    public String verify(@Query("signature") String signature,
                         @Query("timestamp") String timestamp,
                         @Query("nonce") String nonce,
                         @Query("echostr") String echostr,
                         HttpServletRequest request) throws AesException {

        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(echostr)) {
            return "@:";
        }

        String appID = request.getServerName().split("\\.")[0];
        Application app = applicationServiceBean.getApplicationByAppID(appID);
        if (app == null) {
            return "@:";
        }

        String token = app.getToken();
        String actual = SHA1.sign(token, timestamp, nonce, echostr);
        boolean verified = actual.equals(signature);

        Verification verification = new Verification();
        App application = new App(app);
        verification.setApplication(application);
        verification.setMerchant(app.getMerchant());
        verification.setSignature(signature);
        verification.setTimestamp(timestamp);
        verification.setNonce(nonce);
        verification.setEchostr(echostr);
        verification.setToken(token);
        verification.setEncoding(app.getEncoding());
        verification.setSuccess(verified);
        verificationServiceBean.save(verification);

        if (verified) {
            app.setVerified(true);
            app.setDateVerified(new Date());
            applicationServiceBean.update(app);
        } else {
            return "@:";
        }

        EncodingMode mode = app.getEncoding().getMode();
        switch (mode) {
            case PLAIN:
                return "@:" + echostr;
            case COMPATIBLE:
                return "@:" + echostr;
            case ENCRYPTED:
                String password = app.getEncoding().getPassword();
                WXBizMsgCrypt crypt = new WXBizMsgCrypt(token, password, appID);
                return crypt.decrypt(echostr);
            default:
                return "@:";
        }
    }

    @POST("/")
    public String receive() {

        return "@:";
    }

}
