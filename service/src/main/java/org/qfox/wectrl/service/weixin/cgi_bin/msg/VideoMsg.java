package org.qfox.wectrl.service.weixin.cgi_bin.msg;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class VideoMsg extends Msg {
    private static final long serialVersionUID = 7174101324747140811L;

    private VideoBody video;

    public VideoMsg() {
        super("video");
    }

    public VideoMsg(String mediaId, String thumbMediaId, String title, String description) {
        this();
        this.video = new VideoBody(mediaId, thumbMediaId, title, description);
    }

    public VideoBody getVideo() {
        return video;
    }

    public void setVideo(VideoBody video) {
        this.video = video;
    }
}
