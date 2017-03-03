package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.*;
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
@Jestful("/applications")
@Controller
public class ApplicationController {

    @Resource
    private ApplicationService applicationServiceBean;

    @GET("/")
    public String index(@Session(SessionKey.MERCHANT) Merchant merchant,
                        @Query("pagination") int pagination,
                        @Query("capacity") int capacity,
                        HttpServletRequest request) {
        pagination = pagination <= 0 ? 0 : pagination;
        capacity = capacity <= 0 ? 20 : capacity;

        Long merchantId = merchant.getId();
        Page<Application> page = applicationServiceBean.getPagedMerchantApplications(merchantId, pagination, capacity);
        request.setAttribute("page", page);
        return "forward:/view/base/application/index.jsp";
    }

    @GET("/new")
    public String create(@Session(SessionKey.MERCHANT) Merchant merchant) {

        return "forward:/view/base/application/create.jsp";
    }

    @POST("/")
    public String save(@Session(SessionKey.MERCHANT) Merchant merchant) {

        return "redirect:/applications";
    }

}
