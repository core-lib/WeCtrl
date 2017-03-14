package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.Path;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.base.Verification;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.base.VerificationService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Jestful("/applications/{appID:\\w+}/verifications")
@Controller
public class VerificationController {

    @Resource
    private ApplicationService applicationServiceBean;

    @Resource
    private VerificationService verificationServiceBean;

    @GET("/")
    public String index(@Path("appID") String appID,
                        @Query("pagination") int pagination,
                        @Query("capacity") int capacity,
                        HttpServletRequest request) {

        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Application app = applicationServiceBean.getApplicationByAppID(appID);
        request.setAttribute("app", app);

        Page<Verification> page = verificationServiceBean.getPagedApplicationVerifications(appID, pagination, capacity);
        request.setAttribute("page", page);

        return "forward:/view/base/verification/index.jsp";
    }

}
