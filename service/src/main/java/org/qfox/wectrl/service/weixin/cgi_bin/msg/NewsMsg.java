package org.qfox.wectrl.service.weixin.cgi_bin.msg;

import java.util.List;

/**
 * Created by yangchangpei on 17/3/11.
 */
public class NewsMsg extends Msg {
    private static final long serialVersionUID = -8850853033908122239L;

    private NewsBody news;

    public NewsMsg() {
        super("news");
    }

    public NewsMsg(List<Article> articles) {
        this();
        this.news = new NewsBody(articles);
    }

    public NewsBody getNews() {
        return news;
    }

    public void setNews(NewsBody news) {
        this.news = news;
    }
}
