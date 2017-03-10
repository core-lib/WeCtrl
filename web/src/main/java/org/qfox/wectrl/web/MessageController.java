package org.qfox.wectrl.web;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.POST;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.jestful.server.exception.NotFoundStatusException;
import org.qfox.wectrl.common.base.EncodingMode;
import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.base.Verification;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.base.VerificationService;
import org.qfox.wectrl.web.aes.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ApplicationService applicationServiceBean;

    @Resource
    private VerificationService verificationServiceBean;

    @GET("/")
    public String verify(@Query("signature") String signature,
                         @Query("timestamp") String timestamp,
                         @Query("nonce") String nonce,
                         @Query("echostr") String echostr,
                         HttpServletRequest request) {
        Application app = null;
        boolean verified = false;
        try {
            if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(echostr)) {
                throw new IllegalArgumentException("signature/timestamp/nonce/echostr must not null or empty");
            }

            String appID = request.getServerName().split("\\.")[0];
            app = applicationServiceBean.getApplicationByAppID(appID);
            if (app == null) {
                throw new NotFoundStatusException("/message", "GET", null);
            }

            WXBizMsgCrypt crypt = new WXBizMsgCrypt(app.getToken(), app.getEncoding().getPassword(), app.getAppID());
            String result = app.getEncoding().getMode() == EncodingMode.PLAIN ? crypt.verifyPlainURL(signature, timestamp, nonce, echostr) : crypt.verifyEncryptedURL(signature, timestamp, nonce, echostr);
            verified = true;
            return result;
        } catch (Exception e) {
            verified = false;
            logger.error("error occurred when verifying : {}", e);
            return "@:";
        } finally {
            if (verified) {
                app.setVerified(true);
                app.setDateVerified(new Date());
                applicationServiceBean.update(app);
            }

            Verification verification = new Verification();
            App application = app == null ? null : new App(app);
            verification.setApplication(application);
            verification.setMerchant(app == null ? null : app.getMerchant());
            verification.setSignature(signature);
            verification.setTimestamp(timestamp);
            verification.setNonce(nonce);
            verification.setEchostr(echostr);
            verification.setToken(app == null ? null : app.getToken());
            verification.setEncoding(app == null ? null : app.getEncoding());
            verification.setSuccess(verified);
            verificationServiceBean.save(verification);
        }
    }

    @POST("/")
    public String receive() {

        return "@:";
    }

}
