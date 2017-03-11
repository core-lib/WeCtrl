package org.qfox.wectrl.service.weixin.pulling;

import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.service.transaction.SessionProvider;
import org.qfox.wectrl.service.weixin.Language;
import org.qfox.wectrl.service.weixin.TokenService;
import org.qfox.wectrl.service.weixin.UserService;
import org.qfox.wectrl.service.weixin.cgi_bin.PullApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.UserInfoApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.WeixinCgiBinAPI;
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
    private final Application application;
    private final TokenService tokenServiceBean;
    private final SessionProvider sessionProvider;
    private final UserService userServiceBean;
    private final PullResult result = new PullResult(-1, 0, 0);
    private final List<String> openIDs = new ArrayList<>();

    public PullTask(Application application, TokenService tokenServiceBean, SessionProvider sessionProvider, UserService userServiceBean) {
        this.application = application;
        this.tokenServiceBean = tokenServiceBean;
        this.sessionProvider = sessionProvider;
        this.userServiceBean = userServiceBean;
    }

    @Override
    public PullResult call() throws Exception {
        sessionProvider.execute(() -> {
            String nextID = "";
            while (nextID != null) {
                TokenApiResult token = tokenServiceBean.getApplicationAccessToken(application.getAppID());
                if (!token.isSuccess()) {
                    break;
                }
                PullApiResult pull = WeixinCgiBinAPI.INSTANCE.pull(token.getAccess_token(), "".equals(nextID) ? null : nextID);
                if (!pull.isSuccess()) {
                    break;
                }
                openIDs.addAll(pull.getData() != null && pull.getData().getOpenid() != null ? pull.getData().getOpenid() : Arrays.asList());
                result.setTotal(pull.getTotal());
                result.setPulled(result.getPulled() + pull.getCount());
                nextID = StringUtils.isEmpty(pull.getNext_openid()) ? null : pull.getNext_openid();
            }

            for (String openID : openIDs) {
                TokenApiResult token = tokenServiceBean.getApplicationAccessToken(application.getAppID());
                if (!token.isSuccess()) {
                    break;
                }
                UserInfoApiResult info = WeixinCgiBinAPI.INSTANCE.userInfo(token.getAccess_token(), openID, Language.zh_CN);
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
        });
        return result;
    }

}
