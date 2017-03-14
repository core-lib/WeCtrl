package org.qfox.wectrl.dao.base;

import org.qfox.wectrl.core.base.Environment;
import org.qfox.wectrl.dao.GenericDAO;

/**
 * Created by yangchangpei on 17/3/4.
 */
public interface EnvironmentDAO extends GenericDAO<Environment, Long> {

    int updateToVerified(String appID, String envKey);

    int updateToNormal(String appID);

    int deleteByAppIDAndEnvKey(String appID, String envKey);

}
