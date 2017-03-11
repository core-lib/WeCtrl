package org.qfox.wectrl.service.weixin.cgi_bin.msg;

import java.io.Serializable;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class VideoBody implements Serializable {
    private static final long serialVersionUID = -2681582822691557779L;

    private String media_id;
    private String thumb_media_id;
    private String title;
    private String description;

    public VideoBody() {
    }

    public VideoBody(String media_id, String thumb_media_id, String title, String description) {
        this.media_id = media_id;
        this.thumb_media_id = thumb_media_id;
        this.title = title;
        this.description = description;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
