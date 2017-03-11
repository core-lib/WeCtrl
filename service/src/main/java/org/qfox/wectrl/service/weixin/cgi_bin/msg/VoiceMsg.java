package org.qfox.wectrl.service.weixin.cgi_bin.msg;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class VoiceMsg extends Msg {
    private static final long serialVersionUID = 7857752982629142902L;

    private VoiceBody voice;

    public VoiceMsg() {
        super("voice");
    }

    public VoiceMsg(String mediaId) {
        this();
        this.voice = new VoiceBody(mediaId);
    }

    public VoiceBody getVoice() {
        return voice;
    }

    public void setVoice(VoiceBody voice) {
        this.voice = voice;
    }
}
