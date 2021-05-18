package vn.com.mta.science.module.service;

import vn.com.itechcorp.base.repository.service.detail.VoidableGeneratedIDSchemaService;
import vn.com.mta.science.module.model.Invention;
import vn.com.mta.science.module.schema.InventionGet;


public interface InventionService extends VoidableGeneratedIDSchemaService<InventionGet, Invention> {
    InventionGet getByUuid(String uuid);

}
