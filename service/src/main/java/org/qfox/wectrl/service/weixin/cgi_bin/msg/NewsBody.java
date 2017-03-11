package org.qfox.wectrl.service.weixin.cgi_bin.msg;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Description:
 * </p>
 * <p>
 * <p>
 * Company: 广州市俏狐信息科技有限公司
 * </p>
 *
 * @author Payne 646742615@qq.com
 * @date 2016年6月18日 下午5:20:53
 * @since 1.0.0
 */
public class NewsBody implements Serializable {
    private static final long serialVersionUID = 4907859006262713688L;

    private List<Article> articles;

    public NewsBody() {
    }

    public NewsBody(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
