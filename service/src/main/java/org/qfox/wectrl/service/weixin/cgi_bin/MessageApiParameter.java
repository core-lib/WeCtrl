package org.qfox.wectrl.service.weixin.cgi_bin;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Company: 广州市俏狐信息科技有限公司
 * </p>
 * 
 * @author Payne 646742615@qq.com
 *
 * @date 2016年5月14日 下午2:48:06
 *
 * @since 1.0.0
 */
public class MessageApiParameter implements Serializable {
	private static final long serialVersionUID = 5626535611839272531L;

	private String touser;
	private String msgtype;
	private Map<String, String> text;
	private Map<String, String> image;
	private News news;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Map<String, String> getText() {
		return text;
	}

	public void setText(Map<String, String> text) {
		this.text = text;
	}

	public Map<String, String> getImage() {
		return image;
	}

	public void setImage(Map<String, String> image) {
		this.image = image;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	@Override
	public String toString() {
		return "MessageApiParameter [touser=" + touser + ", msgtype=" + msgtype + ", text=" + text + "]";
	}

}
