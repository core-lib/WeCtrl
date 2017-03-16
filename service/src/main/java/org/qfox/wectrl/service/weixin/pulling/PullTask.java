package org.qfox.wectrl.service.weixin.pulling;

import org.qfox.jestful.client.Message;
import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.transaction.SessionProvider;
import org.qfox.wectrl.service.weixin.Language;
import org.qfox.wectrl.service.weixin.UserService;
import org.qfox.wectrl.service.weixin.cgi_bin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by payne on 2017/3/7.
 *
 * @TODO
 */
public class PullTask implements Callable<PullResult> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Application application;
    private final ApplicationService applicationServiceBean;
    private final UserService userServiceBean;
    private final SessionProvider sessionProvider;
    private final PullResult result = new PullResult(-1, 0, 0);
    private final List<String> openIDs = new ArrayList<>();

    public PullTask(Application application,
                    ApplicationService applicationServiceBean,
                    UserService userServiceBean,
                    SessionProvider sessionProvider) {
        this.application = application;
        this.applicationServiceBean = applicationServiceBean;
        this.userServiceBean = userServiceBean;
        this.sessionProvider = sessionProvider;
    }

    @Override
    public PullResult call() throws Exception {
        sessionProvider.execute(() -> {
            try {
                String nextID = "";
                while (nextID != null) {
                    Message<TokenApiResult> msg = WxCtrlCgiBinAPI.INSTANCE.getToken(TokenType.client_credential, application.getAppID(), application.getSecret());
                    if (msg == null || !msg.isSuccess()) {
                        break;
                    }
                    TokenApiResult token = msg.getEntity();
                    if (!token.isSuccess()) {
                        break;
                    }
                    Message<PullApiResult> message = WeixinCgiBinAPI.INSTANCE.pull(token.getAccess_token(), "".equals(nextID) ? null : nextID);
                    if (message == null || !message.isSuccess()) {
                        break;
                    }
                    PullApiResult pull = message.getEntity();
                    if (!pull.isSuccess()) {
                        break;
                    }
                    openIDs.addAll(pull.getData() != null && pull.getData().getOpenid() != null ? pull.getData().getOpenid() : Arrays.asList());
                    result.setTotal(pull.getTotal());
                    result.setPulled(result.getPulled() + pull.getCount());
                    nextID = StringUtils.isEmpty(pull.getNext_openid()) ? null : pull.getNext_openid();
                }

                for (String openID : openIDs) {
                    Message<TokenApiResult> msg = WxCtrlCgiBinAPI.INSTANCE.getToken(TokenType.client_credential, application.getAppID(), application.getSecret());
                    if (msg == null || !msg.isSuccess()) {
                        break;
                    }
                    TokenApiResult token = msg.getEntity();
                    if (!token.isSuccess()) {
                        break;
                    }
                    Message<UserInfoApiResult> message = WeixinCgiBinAPI.INSTANCE.userInfo(token.getAccess_token(), openID, Language.zh_CN);
                    if (message == null || !message.isSuccess()) {
                        continue;
                    }
                    UserInfoApiResult info = message.getEntity();
                    if (!info.isSuccess() || !info.isSubscribe()) {
                        continue;
                    }
                    App app = new App(application);
                    User user = new User();
                    user.setApplication(app);
                    user.setSubscribed(info.isSubscribe());
                    user.setOpenID(info.getOpenid());
                    user.setNickname(info.getNickname());
                    user.setGender(info.getSex());
                    user.setLanguage(info.getLanguage());
                    user.setCity(info.getCity());
                    user.setProvince(info.getProvince());
                    user.setCountry(info.getCountry());
                    user.setPortraitURL(info.getHeadimgurl());
                    user.setDateSubscribed(info.getSubscribe_time() == null ? null : new Date(info.getSubscribe_time() * 1000L));
                    user.setUnionID(info.getUnionid());
                    user.setRemark(info.getRemark());
                    user.setGroupID(info.getGroupid());

                    int count = userServiceBean.merge(user);

                    result.setLoaded(result.getLoaded() + count);
                }
            } catch (Exception e) {
                logger.error("pull user error : {}", e);
                throw e;
            } finally {
                applicationServiceBean.endPulling(application.getAppID());
            }
        });
        return result;
    }

}
