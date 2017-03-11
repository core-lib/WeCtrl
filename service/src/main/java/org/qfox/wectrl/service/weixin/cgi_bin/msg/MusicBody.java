package org.qfox.wectrl.service.weixin.cgi_bin.msg;

import java.io.Serializable;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class MusicBody implements Serializable {
    private static final long serialVersionUID = -2988854773849382427L;

    private String title;
    private String description;
    private String musicurl;
    private String hdmusicurl;
    private String thumb_media_id;

    public MusicBody() {
    }

    public MusicBody(String title, String description, String musicurl, String hdmusicurl, String thumb_media_id) {
        this.title = title;
        this.description = description;
        this.musicurl = musicurl;
        this.hdmusicurl = hdmusicurl;
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

    public String getMusicurl() {
        return musicurl;
    }

    public void setMusicurl(String musicurl) {
        this.musicurl = musicurl;
    }

    public String getHdmusicurl() {
        return hdmusicurl;
    }

    public void setHdmusicurl(String hdmusicurl) {
        this.hdmusicurl = hdmusicurl;
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }
}
