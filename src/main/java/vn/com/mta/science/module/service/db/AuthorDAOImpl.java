package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.mta.science.module.model.Author;

@Repository("authorDAO")
public class AuthorDAOImpl extends VoidableDAOHbnImpl<Author, Long> implements AuthorDAO {
    @Override
    public Class<Author> getEntityClass() {
        return Author.class;
    }
}
