package org.qfox.wectrl.service.weixin.cgi_bin;


import org.qfox.wectrl.service.weixin.ApiResult;

/**
 * Created by yangchangpei on 17/2/7.
 */
public class TicketApiResult extends ApiResult {
    private static final long serialVersionUID = 5765821582539459938L;

    private String ticket;
    private int expires_in;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
