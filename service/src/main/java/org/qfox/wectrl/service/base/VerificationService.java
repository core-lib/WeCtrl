package org.qfox.wectrl.service.base;

import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.base.Verification;
import org.qfox.wectrl.service.GenericService;

/**
 * Created by yangchangpei on 17/3/4.
 */
public interface VerificationService extends GenericService<Verification, Long> {

    Page<Verification> getPagedApplicationVerifications(String appID, int pagination, int capacity);

}
