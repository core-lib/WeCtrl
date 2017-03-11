package org.qfox.wectrl.web.mch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.qfox.wectrl.dao.weixin.WeixinMessageDAO;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by payne on 2017/3/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Rollback
@Transactional
public class HQLTests {

    @Resource
    private WeixinMessageDAO weixinMessageDAO;

    @Test
    public void testPageTexts() throws Exception {
        weixinMessageDAO.getPagedApplicationTexts("wx9e08e4c49d4366d2", 0, 20, null);
    }

}
