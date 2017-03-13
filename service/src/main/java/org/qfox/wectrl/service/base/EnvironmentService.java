package org.qfox.wectrl.service.base;

import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Environment;
import org.qfox.wectrl.service.GenericService;

/**
 * Created by yangchangpei on 17/3/4.
 */
public interface EnvironmentService extends GenericService<Environment, Long> {

    Page<Environment> getPagedApplicationEnvironments(String appID, int pagination, int capacity);

    boolean isApplicationEnvKeyExisted(String appID, String envKey);

    Environment getApplicationEnvironment(String appID, String envKey, String... fetchs);

    int updateToVerified(String appID, String envKey);

}
