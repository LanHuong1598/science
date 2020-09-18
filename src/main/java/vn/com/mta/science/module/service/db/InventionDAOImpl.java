package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.mta.science.module.model.Invention;

@Repository("inventionDAO")
public class InventionDAOImpl extends VoidableDAOHbnImpl<Invention, Long> implements InventionDAO {
    @Override
    public Class<Invention> getEntityClass() {
        return Invention.class;
    }
}
