package org.qfox.wectrl.service.weixin.cgi_bin.msg;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class ImageMsg extends Msg {
    private static final long serialVersionUID = 8367220357910307833L;

    private ImageBody image;

    public ImageMsg() {
        super("image");
    }

    public ImageMsg(String mediaId) {
        this();
        this.image = new ImageBody(mediaId);
    }

    public ImageBody getImage() {
        return image;
    }

    public void setImage(ImageBody image) {
        this.image = image;
    }
}
