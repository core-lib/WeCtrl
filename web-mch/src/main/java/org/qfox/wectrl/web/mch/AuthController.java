package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.*;
import org.qfox.jestful.server.annotation.Session;
import org.qfox.wectrl.core.base.Merchant;
import org.qfox.wectrl.service.base.MerchantService;
import org.qfox.wectrl.web.mch.auth.Authorized;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Jestful("/auth")
@Controller
public class AuthController {

    @Resource
    private MerchantService merchantServiceBean;

    @Authorized(required = false)
    @GET("/login")
    public String login(@Session(SessionKey.MERCHANT) Merchant merchant,
                        @Query("redirectURI") String redirectURI,
                        @Query("error") String error,
                        HttpServletRequest request) {
        if (merchant != null) {
            return "redirect:/";
        }

        request.setAttribute("redirectURI", redirectURI == null || redirectURI.trim().isEmpty() ? "/" : redirectURI.trim());
        request.setAttribute("error", error);

        return "forward:/view/auth/login.jsp";
    }

    @Authorized(required = false)
    @POST("/login")
    public String login(@Body("username") String username,
                        @Body("password") String password,
                        @Body("redirectURI") String redirectURI,
                        HttpSession session) throws UnsupportedEncodingException {
        Merchant merchant = merchantServiceBean.login(username, password);
        if (merchant == null || merchant.isDeleted()) {
            redirectURI = redirectURI == null || redirectURI.trim().isEmpty() ? "/" : redirectURI.trim();
            return "redirect:/auth/login?redirectURI=" + URLEncoder.encode(redirectURI, "UTF-8") + "&error=" + URLEncoder.encode("用户名或密码错误", "UTF-8");
        }

        session.setAttribute(SessionKey.MERCHANT, merchant);

        return "redirect:/";
    }

    @Authorized(required = false)
    @GET("/logout")
    public String logout(HttpSession session) throws UnsupportedEncodingException {
        Enumeration<String> enumeration = session.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            session.removeAttribute(name);
        }
        return "redirect:/";
    }

}
