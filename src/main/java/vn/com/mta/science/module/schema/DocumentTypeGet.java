package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.Classification;
import vn.com.mta.science.module.model.DocumentType;

@Getter
@Setter
@NoArgsConstructor
public class DocumentTypeGet extends SchemaGet<DocumentType, Long> {

    private String name;

    private String description;

    public DocumentTypeGet(DocumentType group) {
        super(group);
    }

    @Override
    public void parse(DocumentType group) {
        this.name = group.getName();
        this.description = group.getDescription();
    }
}
