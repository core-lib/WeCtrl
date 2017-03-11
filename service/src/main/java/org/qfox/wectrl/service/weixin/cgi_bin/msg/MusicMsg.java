package org.qfox.wectrl.service.weixin.cgi_bin.msg;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class MusicMsg extends Msg {
    private static final long serialVersionUID = -6715854384636783468L;

    private MusicBody music;

    public MusicMsg() {
        super("music");
    }

    public MusicMsg(String title, String description, String musicURL, String hdMusicURL, String thumbMediaId) {
        this();
        this.music = new MusicBody(title, description, musicURL, hdMusicURL, thumbMediaId);
    }

    public MusicBody getMusic() {
        return music;
    }

    public void setMusic(MusicBody music) {
        this.music = music;
    }
}
