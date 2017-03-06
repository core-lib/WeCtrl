package org.qfox.wectrl.service.weixin.pulling;

import java.io.Serializable;

/**
 * Created by payne on 2017/3/7.
 */
public class PullResult implements Serializable {
    private static final long serialVersionUID = -3584913207197796682L;

    private int total;
    private int pulled;
    private int loaded;

    public PullResult() {
    }

    public PullResult(int total, int pulled, int loaded) {
        this.total = total;
        this.pulled = pulled;
        this.loaded = loaded;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPulled() {
        return pulled;
    }

    public void setPulled(int pulled) {
        this.pulled = pulled;
    }

    public int getLoaded() {
        return loaded;
    }

    public void setLoaded(int loaded) {
        this.loaded = loaded;
    }
}
