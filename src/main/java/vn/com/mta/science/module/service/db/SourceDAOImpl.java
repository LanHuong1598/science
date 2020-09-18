package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.mta.science.module.model.Source;

@Repository("sourceDAO")
public class SourceDAOImpl extends VoidableDAOHbnImpl<Source, Long> implements SourceDAO {
    @Override
    public Class<Source> getEntityClass() {
        return Source.class;
    }
}
