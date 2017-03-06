package org.qfox.wectrl.service.transaction;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

/**
 * Created by yangchangpei on 17/2/24.
 */
@Component
public class DefaultSessionProvider implements SessionProvider {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void execute(SessionBlock block) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.setFlushMode(FlushMode.MANUAL);
            SessionHolder holder = new SessionHolder(session);
            TransactionSynchronizationManager.bindResource(sessionFactory, holder);
            block.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
                TransactionSynchronizationManager.unbindResource(sessionFactory);
            }
        }
    }

}
