package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.jestful.server.annotation.Session;
import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.base.Merchant;
import org.qfox.wectrl.service.base.ApplicationService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Jestful("/")
@Controller
public class IndexController {

    @Resource
    private ApplicationService applicationServiceBean;

    @GET("/")
    public String index(@Query("pagination") int pagination,
                        @Query("capacity") int capacity,
                        @Query("keyword") String keyword,
                        @Session(SessionKey.MERCHANT) Merchant merchant,
                        HttpServletRequest request) {
        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Long merchantId = merchant.getId();
        Page<Application> page = applicationServiceBean.getPagedMerchantApplications(merchantId, pagination, capacity, keyword);
        request.setAttribute("page", page);

        return "forward:/view/index.jsp";
    }

}
