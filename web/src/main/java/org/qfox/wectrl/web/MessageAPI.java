package org.qfox.wectrl.web;

import org.qfox.jestful.client.scheduler.Callback;
import org.qfox.jestful.core.annotation.Body;
import org.qfox.jestful.core.annotation.Jestful;
import org.qfox.jestful.core.annotation.POST;
import org.qfox.jestful.core.annotation.Query;
import org.qfox.wectrl.web.msg.Data;

/**
 * Created by yangchangpei on 17/3/17.
 */
@Jestful("/")
public interface MessageAPI {

    @POST(value = "/", consumes = "text/xml")
    void push(@Query("signature") String signature,
              @Query("timestamp") String timestamp,
              @Query("nonce") String nonce,
              @Query("encrypt_type") String encryptType,
              @Query("msg_signature") String msgSignature,
              @Body Data data,
              Callback<String> callback);

}
