package vn.com.mta.science.module.service;

import vn.com.itechcorp.base.repository.service.detail.VoidableGeneratedIDSchemaService;
import vn.com.itechcorp.base.util.Pair;
import vn.com.mta.science.module.model.Author;
import vn.com.mta.science.module.schema.AuthorGet;
import vn.com.mta.science.module.service.filter.AuthorByAffFilter;

import java.util.Collection;


public interface AuthorService extends VoidableGeneratedIDSchemaService<AuthorGet, Author> {

    Pair<Collection<AuthorGet>, Long> getByAff(AuthorByAffFilter filter);
}
