package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.DocumentType;

@Getter
@Setter
@NoArgsConstructor
public class DocumentTypeCreate extends GeneratedIDSchemaCreate<DocumentType> {

    private String name;

    private String description;

    @Override
    public DocumentType toEntry() {
        DocumentType newGroup = new DocumentType();
        newGroup.setName(name);
        newGroup.setDescription(description);
        return newGroup;
    }
}
