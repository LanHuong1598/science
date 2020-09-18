package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.Language;

@Getter
@Setter
@NoArgsConstructor
public class LanguageCreate extends GeneratedIDSchemaCreate<Language> {

    private String name;

    private String description;

    @Override
    public Language toEntry() {
        Language newGroup = new Language();
        newGroup.setName(name);
        newGroup.setDescription(description);
        return newGroup;
    }
}
