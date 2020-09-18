package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.mta.science.module.model.Major;

@Repository("majorDAO")
public class MajorDAOImpl extends VoidableDAOHbnImpl<Major, Long> implements MajorDAO {
    @Override
    public Class<Major> getEntityClass() {
        return Major.class;
    }
}
