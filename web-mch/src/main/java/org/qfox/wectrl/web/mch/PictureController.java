package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.wectrl.service.base.FileService;
import org.qfox.wectrl.service.base.PictureService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by yangchangpei on 17/2/7.
 */
@Jestful("/picture")
@Controller
public class PictureController {

    @Resource
    private FileService fileServiceBean;

    @Resource
    private PictureService pictureServiceBean;



}
