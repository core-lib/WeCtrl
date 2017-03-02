package org.qfox.wectrl.dao.impl.base;

import org.qfox.wectrl.core.base.Picture;
import org.qfox.wectrl.dao.base.PictureDAO;
import org.qfox.wectrl.dao.impl.HibernateGenericDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchangpei on 17/3/2.
 */
@Repository
public class HibernatePictureDAO extends HibernateGenericDAO<Picture, Long> implements PictureDAO {
}
