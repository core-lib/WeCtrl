package org.qfox.wectrl.service.transaction;

/**
 * Created by yangchangpei on 17/2/24.
 */
public interface SessionProvider {

    void execute(SessionBlock block);

}
