package org.qfox.wectrl.web.mch.auth;

import org.qfox.jestful.core.Action;
import org.qfox.wectrl.web.mch.SessionKey;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Component
public class MerchantSessionRebuildActor implements SessionRebuildActor {

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public Object react(Action action) throws Exception {
        HttpServletRequest request = (HttpServletRequest) action.getRequest();
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String redirectURI = uri + (query == null || query.isEmpty() ? "" : ("?" + query));
        // 如果用户没登录
        if (session.getAttribute(SessionKey.MERCHANT) == null) {
            return "redirect:/auth/login?redirectURI=" + URLEncoder.encode(redirectURI, "UTF-8");
        }

        return action.execute();
    }

}
