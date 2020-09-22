package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaCreate;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.Cited;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CitedGet implements Serializable {

    private String name;

    private String description;

    private Long documentId;

    private String doi;

    private String publisher;

    private String issn;

    private String url;

    public CitedGet(Cited cited) {
        setDescription(cited.getDescription());
        setDoi(cited.getDoi());
        setPublisher(cited.getPublisher());
        setName(cited.getName());
        setDocumentId(cited.getDocument_id());
        setUrl(cited.getUrl());
        setIssn(cited.getIssn());
    }
}
