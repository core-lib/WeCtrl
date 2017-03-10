package org.qfox.wectrl.web.auth;

import org.qfox.jestful.core.*;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by yangchangpei on 16/11/22.
 */
@Component("extraAttributePlugin")
public class ExtraAttributePlugin extends Group implements Initialable, Comparator<ExtraAttributeResolver> {

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
        Map<String, ExtraAttributeResolver> map = beanContainer.find(ExtraAttributeResolver.class);
        List<ExtraAttributeResolver> resolvers = new ArrayList<ExtraAttributeResolver>(map.values());
        Collections.sort(resolvers, this);
        this.members.addAll(resolvers);
    }

    @Override
    public int compare(ExtraAttributeResolver a, ExtraAttributeResolver b) {
        return a.getIndex() > b.getIndex() ? 1 : a.getIndex() < b.getIndex() ? -1 : 0;
    }

}
