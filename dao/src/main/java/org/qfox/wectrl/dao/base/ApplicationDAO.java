package org.qfox.wectrl.dao.base;

import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.dao.GenericDAO;

/**
 * Created by yangchangpei on 17/2/28.
 */
public interface ApplicationDAO extends GenericDAO<Application, Long> {

    boolean startPulling(String appID);

    boolean endPulling(String appID);

    boolean startRefreshing(String appID);

    boolean endRefreshing(String appID);

    int updateToVerified(String appID);

}
