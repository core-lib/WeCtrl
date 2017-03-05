package org.qfox.wectrl.service.weixin.cgi_bin;

import java.io.Serializable;
import java.util.List;

/**
 * Created by payne on 2017/2/19.
 */
public class MenuApiParameter implements Serializable {
    private static final long serialVersionUID = -7846536313245561677L;

    private List<Button> button;

    public List<Button> getButton() {
        return button;
    }

    public void setButton(List<Button> button) {
        this.button = button;
    }
}
