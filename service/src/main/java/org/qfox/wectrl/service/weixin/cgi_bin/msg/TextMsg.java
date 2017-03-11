package org.qfox.wectrl.service.weixin.cgi_bin.msg;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class TextMsg extends Msg {
    private static final long serialVersionUID = -1972644706366318068L;

    private TextBody text;

    public TextMsg() {
        super("text");
    }

    public TextMsg(String content) {
        this();
        this.text = new TextBody(content);
    }

    public TextBody getText() {
        return text;
    }

    public void setText(TextBody text) {
        this.text = text;
    }
}
