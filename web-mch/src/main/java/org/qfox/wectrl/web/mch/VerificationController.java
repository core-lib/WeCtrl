package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.jestful.server.annotation.Session;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Merchant;
import org.qfox.wectrl.core.base.Verification;
import org.qfox.wectrl.service.base.VerificationService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangchangpei on 17/3/4.
 */
@Jestful("/verifications")
public class VerificationController {

    @Resource
    private VerificationService verificationServiceBean;

    @GET("/")
    public String index(@Session(SessionKey.MERCHANT) Merchant merchant,
                        @Query("pagination") int pagination,
                        @Query("capacity") int capacity,
                        HttpServletRequest request) {

        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Long merchantId = merchant.getId();
        Page<Verification> page = verificationServiceBean.getPagedMerchantVerifications(merchantId, pagination, capacity);
        request.setAttribute("page", page);

        return "forward:/view/base/verification/index.jsp";
    }

}
