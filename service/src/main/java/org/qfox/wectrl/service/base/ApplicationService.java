package org.qfox.wectrl.service.base;

import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.service.GenericService;

/**
 * Created by yangchangpei on 17/2/28.
 */
public interface ApplicationService extends GenericService<Application, Long> {

    Page<Application> getPagedMerchantApplications(Long merchantId, int pagination, int capacity, String keyword, String... fetchs);

    boolean isAppIDExisted(String appID);

    boolean isOriginalIDExisted(String originalID);

    Application getApplicationByAppID(String appID, String... fetchs);

    boolean startPulling(String appID);

    boolean endPulling(String appID);

    boolean startRefreshing(String appID);

    boolean endRefreshing(String appID);

    int updateToVerified(String appID);

}
