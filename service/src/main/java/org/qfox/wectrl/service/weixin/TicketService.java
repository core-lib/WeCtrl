package org.qfox.wectrl.service.weixin;

import org.qfox.wectrl.common.Page;
import org.qfox.wectrl.common.weixin.TicketType;
import org.qfox.wectrl.core.weixin.Ticket;
import org.qfox.wectrl.core.weixin.Token;
import org.qfox.wectrl.service.GenericService;
import org.qfox.wectrl.service.weixin.cgi_bin.TicketApiResult;

/**
 * Created by payne on 2017/3/5.
 */
public interface TicketService extends GenericService<Ticket, Long> {

    TicketApiResult getApplicationJSAPITicket(String accessToken);

    TicketApiResult newApplicationJSAPITicket(String accessToken);

    TicketApiResult getApplicationWXCardTicket(String accessToken);

    TicketApiResult newApplicationWXCardTicket(String accessToken);

    Page<Ticket> getPagedApplicationTickets(String appID, TicketType type, int pagination, int capacity);

}
