package org.qfox.wectrl.service.weixin.cgi_bin.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description:含有二级按钮的复杂按钮
 * </p>
 * <p>
 * <p>
 * Company: 广州市俏狐信息科技有限公司
 * </p>
 *
 * @author zbl 727513242@qq.com
 * @date 2016年7月24日
 */
public class ParentButton extends Button {
    private static final long serialVersionUID = -5514243428453474419L;

    private List<SimpleButton> sub_button = new ArrayList<>();

    public ParentButton(String name) {
        super(name);
    }

    public ParentButton(String name, List<SimpleButton> sub_button) {
        this(name);
        this.sub_button = sub_button;
    }

    public List<SimpleButton> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<SimpleButton> sub_button) {
        this.sub_button = sub_button;
    }
}
