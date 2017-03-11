package org.qfox.wectrl.service.weixin.cgi_bin.menu;

import java.io.Serializable;

/**
 * <p>
 * Description:按钮基类
 * </p>
 * <p>
 * <p>
 * Company: 广州市俏狐信息科技有限公司
 * </p>
 *
 * @author zbl 727513242@qq.com
 * @date 2016年7月23日
 */
public abstract class Button implements Serializable {
    private static final long serialVersionUID = 4094179333421995476L;

    private final String name;

    protected Button(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
