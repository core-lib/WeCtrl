package org.qfox.wectrl.service.base;

import org.qfox.wectrl.core.base.Merchant;
import org.qfox.wectrl.service.GenericService;

/**
 * Created by yangchangpei on 17/3/2.
 */
public interface MerchantService extends GenericService<Merchant, Long> {

    Merchant login(String username, String password, String... fetchs);

    boolean isUsernameUsed(String username);

    boolean isEmailBound(String email);

    boolean isCellphoneBound(String cellphone);

}
