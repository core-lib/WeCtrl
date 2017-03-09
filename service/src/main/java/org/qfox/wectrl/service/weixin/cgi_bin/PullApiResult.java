package org.qfox.wectrl.service.weixin.cgi_bin;

import org.qfox.wectrl.service.weixin.ApiResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by payne on 2017/3/7.
 */
public class PullApiResult extends ApiResult {
    private static final long serialVersionUID = 8071892032158486040L;

    private int total;
    private int count;
    private Data data = new Data();
    private String next_openid;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getNext_openid() {
        return next_openid;
    }

    public void setNext_openid(String next_openid) {
        this.next_openid = next_openid;
    }

    public static class Data {
        private List<String> openid = new ArrayList<>();

        public List<String> getOpenid() {
            return openid;
        }

        public void setOpenid(List<String> openid) {
            this.openid = openid;
        }
    }

}
