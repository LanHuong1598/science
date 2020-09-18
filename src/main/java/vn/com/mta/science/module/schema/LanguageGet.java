package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.Language;

@Getter
@Setter
@NoArgsConstructor
public class LanguageGet extends SchemaGet<Language, Long> {

    private String name;

    private String description;

    public LanguageGet(Language group) {
        super(group);
    }

    @Override
    public void parse(Language group) {
        this.name = group.getName();
        this.description = group.getDescription();
    }
}
