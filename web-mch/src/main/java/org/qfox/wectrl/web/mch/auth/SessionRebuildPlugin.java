package org.qfox.wectrl.web.mch.auth;

import org.qfox.jestful.core.*;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by yangchangpei on 16/11/22.
 */
@Component("sessionRebuildPlugin")
public class SessionRebuildPlugin extends Group implements Initialable, Comparator<SessionRebuildActor> {

    @Override
    public Object react(Action action) throws Exception {
        Mapping mapping = action.getMapping();
        Resource resource = action.getResource();
        Authorized authorized = mapping.isAnnotationPresent(Authorized.class) ? mapping.getAnnotation(Authorized.class) : resource.isAnnotationPresent(Authorized.class) ? resource.getAnnotation(Authorized.class) : null;

        // 如果不需要授权
        if (authorized != null && authorized.required() == false) {
            return action.execute();
        }

        return super.react(action);
    }

    @Override
    public void initialize(BeanContainer beanContainer) {
        Map<String, SessionRebuildActor> map = beanContainer.find(SessionRebuildActor.class);
        List<SessionRebuildActor> actors = new ArrayList<SessionRebuildActor>(map.values());
        Collections.sort(actors, this);
        this.members.addAll(actors);
    }

    @Override
    public int compare(SessionRebuildActor a, SessionRebuildActor b) {
        return a.getIndex() > b.getIndex() ? 1 : a.getIndex() < b.getIndex() ? -1 : 0;
    }

}
