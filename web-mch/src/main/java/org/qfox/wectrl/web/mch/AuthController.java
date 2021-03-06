package org.qfox.wectrl.web.mch;

import org.apache.commons.codec.binary.Hex;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Jestful("/auth")
@Controller
@Authorized(required = false)
public class AuthController {

    private final MessageDigest MD5;

    public AuthController() throws NoSuchAlgorithmException {
        this.MD5 = MessageDigest.getInstance("MD5");
    }

    @Resource
    private MerchantService merchantServiceBean;

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

    @POST("/login")
    public String login(@Body("username") String username,
                        @Body("password") String password,
                        @Body("redirectURI") String redirectURI,
                        HttpSession session) throws UnsupportedEncodingException {
        redirectURI = redirectURI == null || redirectURI.trim().isEmpty() ? "/" : redirectURI.trim();

        Merchant merchant = merchantServiceBean.login(username, password);
        if (merchant == null || merchant.isDeleted()) {
            return "redirect:/auth/login?redirectURI=" + URLEncoder.encode(redirectURI, "UTF-8") + "&error=" + URLEncoder.encode("用户名或密码错误", "UTF-8");
        }

        if (merchant.isActivated() == false) {
            return "redirect:/auth/login?redirectURI=" + URLEncoder.encode(redirectURI, "UTF-8") + "&error=" + URLEncoder.encode("账户未激活, 请登录邮箱激活!", "UTF-8");
        }

        session.setAttribute(SessionKey.MERCHANT, merchant);

        return "redirect:" + redirectURI;
    }

    @GET("/logout")
    public String logout(HttpSession session) throws UnsupportedEncodingException {
        Enumeration<String> enumeration = session.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            session.removeAttribute(name);
        }
        return "redirect:/";
    }

    @GET("/register")
    public String register(@Query("error") String error, HttpServletRequest request) {
        request.setAttribute("error", error);
        return "forward:/view/auth/register.jsp";
    }

    @POST("/register")
    public String register(@Body("name") String name,
                           @Body("username") String username,
                           @Body("password") String password,
                           @Body("email") String email,
                           HttpServletRequest request) throws UnsupportedEncodingException {

        if (name == null || !name.matches("^[a-zA-Z0-9\\u4e00-\\u9fa5]{1,24}$")) {
            return "redirect:/auth/register?error=" + URLEncoder.encode("请填写长度在1到24位的名称且不能包括特殊字符!", "UTF-8");
        }
        if (username == null || !username.matches("^(?![0-9_])[a-zA-Z0-9_]{6,12}$")) {
            return "redirect:/auth/register?error=" + URLEncoder.encode("请填写长度为6到12位以字母开头且只包含字母,数字,下划线的用户名!", "UTF-8");
        }
        if (password == null || !password.matches("\\w{6,12}")) {
            return "redirect:/auth/register?error=" + URLEncoder.encode("请填写6到12位且只包含字母,数字,下划线的密码!", "UTF-8");
        }
        if (email == null || !email.matches("^[a-zA-Z0-9]+([._\\\\-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+[-a-zA-Z0-9]*[a-zA-Z0-9]+.){1,63}[a-zA-Z0-9]+$")) {
            return "redirect:/auth/register?error=" + URLEncoder.encode("请填写正确的邮箱地址因为需要验证!", "UTF-8");
        }

        if (merchantServiceBean.isUsernameUsed(username)) {
            return "redirect:/auth/register?error=" + URLEncoder.encode("用户名已存在!", "UTF-8");
        }

        if (merchantServiceBean.isEmailBound(email)) {
            return "redirect:/auth/register?error=" + URLEncoder.encode("用户名已存在邮箱已经被使用!", "UTF-8");
        }

        Merchant merchant = new Merchant();
        merchant.setName(name);
        merchant.setUsername(username);
        merchant.setPassword(Hex.encodeHexString(MD5.digest(password.getBytes())));
        merchant.setEmail(email);
        merchantServiceBean.save(merchant);

        return "redirect:/";
    }

}
