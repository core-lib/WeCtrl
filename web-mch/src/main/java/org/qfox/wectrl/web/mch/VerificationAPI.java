package org.qfox.wectrl.web.mch;

import org.qfox.jestful.core.annotation.GET;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.POST;
import org.qfox.jestful.core.annotation.Query;

/**
 * Created by payne on 2017/3/12.
 */
@Jestful("/")
public interface VerificationAPI {

    @GET("/")
    String get(@Query("signature") String signature,
               @Query("timestamp") String timestamp,
               @Query("nonce") String nonce,
               @Query("echostr") String echostr);

    @POST("/")
    String post(@Query("signature") String signature,
                @Query("timestamp") String timestamp,
                @Query("nonce") String nonce,
                @Query("echostr") String echostr);

}
