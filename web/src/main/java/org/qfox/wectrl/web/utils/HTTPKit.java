package org.qfox.wectrl.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by payne on 2017/1/30.
 */
public abstract class HTTPKit {

    public static String getClosestScheme(HttpServletRequest request, String defaultValue) {
        // 兼容 Nginx + tomcat 的方式 屏蔽了 HTTPS 的逻辑
        String scheme = request.getHeader("X-Forwarded-Scheme");
        if (scheme == null || scheme.trim().isEmpty()) {
            scheme = request.getScheme();
        }
        return scheme == null || scheme.trim().isEmpty() ? defaultValue : scheme;
    }

}
