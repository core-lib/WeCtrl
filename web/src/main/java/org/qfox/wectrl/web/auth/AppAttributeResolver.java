package org.qfox.wectrl.web.auth;

import org.qfox.jestful.core.Action;
import org.qfox.jestful.core.Parameter;
import org.qfox.jestful.core.annotation.Variable;
import org.qfox.jestful.server.exception.NotFoundStatusException;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.web.utils.HTTPKit;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by payne on 2017/3/10.
 */
@Component
public class AppAttributeResolver implements ExtraAttributeResolver {
    private final Map<String, AppHolder> cache = new HashMap<>();

    @Resource
    private ApplicationService applicationServiceBean;

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public Object react(Action action) throws Exception {
        Iterator<Parameter> iterator = action.getParameters()
                .parallelStream()
                .filter(parameter -> parameter.getAnnotationWith(Variable.class) == null)
                .filter(parameter -> parameter.getKlass() == Application.class)
                .iterator();

        if (!iterator.hasNext()) {
            return action.execute();
        }

        HttpServletRequest request = (HttpServletRequest) action.getRequest();
        String appID = request.getServerName().split("\\.")[0];
        AppHolder holder = cache.get(appID);
        if (holder == null || System.currentTimeMillis() - holder.timeHolded > 1L * 60L) {
            synchronized (appID.intern()) {
                holder = cache.get(appID);
                if (holder == null || System.currentTimeMillis() - holder.timeHolded > 1L * 60L) {
                    Application application = applicationServiceBean.getApplicationByAppID(appID);
                    if (application == null) {
                        String host = request.getServerName();
                        String path = request.getRequestURI();
                        String query = request.getQueryString();
                        String scheme = HTTPKit.getClosestScheme(request, "http");
                        String url = scheme + "://" + host + path + (query != null && query.trim().length() > 0 ? "?" + query.trim() : "");
                        String method = request.getMethod();
                        String version = action.getMapping().getVersion();
                        throw new NotFoundStatusException(url, method, version);
                    }
                    applicationServiceBean.evict(application);
                    cache.put(appID, holder = new AppHolder(application));
                }
            }
        }

        AppHolder finalHolder = holder;
        iterator.forEachRemaining(parameter -> parameter.setValue(finalHolder.application));

        return action.execute();
    }

    private static class AppHolder {
        private final Application application;
        private final long timeHolded;

        public AppHolder(Application application) {
            this.application = application;
            this.timeHolded = System.currentTimeMillis();
        }

    }
}
