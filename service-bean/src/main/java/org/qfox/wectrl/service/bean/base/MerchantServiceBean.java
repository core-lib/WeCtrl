package org.qfox.wectrl.service.bean.base;

import org.apache.commons.codec.binary.Hex;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.qfox.wectrl.core.base.Merchant;
import org.qfox.wectrl.dao.GenericDAO;
import org.qfox.wectrl.dao.base.MerchantDAO;
import org.qfox.wectrl.service.base.MerchantService;
import org.qfox.wectrl.service.bean.GenericServiceBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Service
public class MerchantServiceBean extends GenericServiceBean<Merchant, Long> implements MerchantService {

    private final MessageDigest MD5;

    public MerchantServiceBean() throws NoSuchAlgorithmException {
        this.MD5 = MessageDigest.getInstance("MD5");
    }

    @Resource
    private MerchantDAO merchantDAO;

    @Override
    protected GenericDAO<Merchant, Long> getEntityDAO() {
        return merchantDAO;
    }

    @Override
    public Merchant login(String username, String password, String... fetchs) {
        Criteria criteria = merchantDAO.createCriteria();
        criteria.add(Restrictions.or(Restrictions.eq("username", username), Restrictions.eq("cellphone", username), Restrictions.eq("email", username)));
        String encoded = Hex.encodeHexString(MD5.digest(password.getBytes()));
        criteria.add(Restrictions.eq("password", encoded));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for (String fetch : fetchs) {
            criteria.setFetchMode(fetch, FetchMode.JOIN);
        }
        return (Merchant) criteria.uniqueResult();
    }

    @Override
    public boolean isUsernameUsed(String username) {
        Criteria criteria = merchantDAO.createCriteria();
        criteria.setProjection(Projections.count("id"));
        criteria.add(Restrictions.eq("username", username));
        Object count = criteria.uniqueResult();
        return count != null && Integer.valueOf(count.toString()) > 0;
    }

    @Override
    public boolean isEmailBound(String email) {
        Criteria criteria = merchantDAO.createCriteria();
        criteria.setProjection(Projections.count("id"));
        criteria.add(Restrictions.eq("email", email));
        Object count = criteria.uniqueResult();
        return count != null && Integer.valueOf(count.toString()) > 0;
    }

    @Override
    public boolean isCellphoneBound(String cellphone) {
        Criteria criteria = merchantDAO.createCriteria();
        criteria.setProjection(Projections.count("id"));
        criteria.add(Restrictions.eq("cellphone", cellphone));
        Object count = criteria.uniqueResult();
        return count != null && Integer.valueOf(count.toString()) > 0;
    }
}
