package org.qfox.wectrl.service.weixin.cgi_bin.menu;

import org.qfox.wectrl.service.weixin.cgi_bin.ButtonType;

/**
 * <p>
 * Description:有实际功能的普通按钮
 * </p>
 * <p>
 * <p>
 * Company: 广州市俏狐信息科技有限公司
 * </p>
 *
 * @author zbl 727513242@qq.com
 * @date 2016年7月23日
 */
public class SimpleButton extends Button {
    private ButtonType type;
    private String key;
    private String url;
    private String media_id;

    public SimpleButton(String name) {
        super(name);
    }

    public SimpleButton(String name, ButtonType type, String key, String url, String media_id) {
        this(name);
        this.type = type;
        this.key = key;
        this.url = url;
        this.media_id = media_id;
    }

    public ButtonType getType() {
        return type;
    }

    public void setType(ButtonType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
}
