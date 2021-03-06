package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.Cited;

@Getter
@Setter
@NoArgsConstructor
public class CitedGet extends SchemaGet<Cited, Long>  {

    private String name;

    private String description;

    private Long documentId;

    private String doi;

    private String publisher;

    private String issn;

    private String url;

    public CitedGet(Cited cited) {
        setId(cited.getId());
        setDescription(cited.getDescription());
        setDoi(cited.getDoi());
        setPublisher(cited.getPublisher());
        setName(cited.getName());
        setDocumentId(cited.getDocument_id());
        setUrl(cited.getUrl());
        setIssn(cited.getIssn());
    }

    @Override
    public void parse(Cited cited) {
        setDescription(cited.getDescription());
        setDoi(cited.getDoi());
        setPublisher(cited.getPublisher());
        setName(cited.getName());
        setDocumentId(cited.getDocument_id());
        setUrl(cited.getUrl());
        setIssn(cited.getIssn());
    }
}
