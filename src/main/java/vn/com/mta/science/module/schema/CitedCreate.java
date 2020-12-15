package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.Cited;

@Getter
@Setter
@NoArgsConstructor
public class CitedCreate extends GeneratedIDSchemaCreate<Cited> {

    private String name;

    private String description;

    private Long documentId;

    private String doi;

    private String publisher;

    private String issn;

    private String url;

    @Override
    public Cited toEntry() {
        Cited document = new Cited();
        document.setDescription(description);
        document.setDoi(doi);
        document.setPublisher(publisher);
        document.setName(name);
        document.setDocument_id(documentId);
        document.setUrl(url);
        document.setIssn(issn);
        return document;
    }
}
