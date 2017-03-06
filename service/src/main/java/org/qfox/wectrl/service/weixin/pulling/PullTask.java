package org.qfox.wectrl.service.weixin.pulling;

import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.service.transaction.SessionProvider;
import org.qfox.wectrl.service.weixin.Language;
import org.qfox.wectrl.service.weixin.TokenService;
import org.qfox.wectrl.service.weixin.cgi_bin.PullApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.TokenApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.UserInfoApiResult;
import org.qfox.wectrl.service.weixin.cgi_bin.WeixinCgiBinAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by payne on 2017/3/7.
 *
 * @TODO
 */
public class PullTask implements Callable<PullResult> {
    private final WeixinCgiBinAPI weixinCgiBinAPI;
    private final Application application;
    private final TokenService tokenServiceBean;
    private final SessionProvider sessionProvider;
    private final PullResult result = new PullResult(-1, 0, 0);
    private final List<String> openIDs = new ArrayList<>();

    public PullTask(WeixinCgiBinAPI weixinCgiBinAPI, Application application, TokenService tokenServiceBean, SessionProvider sessionProvider) {
        this.weixinCgiBinAPI = weixinCgiBinAPI;
        this.application = application;
        this.tokenServiceBean = tokenServiceBean;
        this.sessionProvider = sessionProvider;
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
                PullApiResult pull = weixinCgiBinAPI.pull(token.getAccess_token(), "".equals(nextID) ? null : nextID);
                if (!pull.isSuccess()) {
                    break;
                }
                openIDs.addAll(pull.getData().getOpenid());
                result.setTotal(pull.getTotal());
                result.setPulled(result.getPulled() + pull.getCount());
                nextID = pull.getNext_openid();
            }
            for (String openID : openIDs) {
                TokenApiResult token = tokenServiceBean.getApplicationAccessToken(application.getAppID());
                if (!token.isSuccess()) {
                    break;
                }
                UserInfoApiResult result = weixinCgiBinAPI.userInfo(token.getAccess_token(), openID, Language.zh_CN);
                if (!result.isSuccess()) {
                    continue;
                }

            }
        });
        return result;
    }

}
