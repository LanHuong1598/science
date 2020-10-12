package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaUpdate;
import vn.com.mta.science.module.model.Document;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class DocumentUpdate extends SchemaUpdate<Document, Long> {

    private String description;

    private String sourceId;

    private String doi;

    private String publisher;

    private String publicationIndex;

    private String publishDate;

    private Long languageId;

    private String title;

    private String abstractText;

    private Long classificationId;

    private Long documentType;

    private Long groupId;

    private Long majorId;

    private Long specializationId;

    private Long citedNumber;

    private Collection<String> keyword;

    private Long mtaJournalId;

    private MultipartFile attachmentsFullText;

    private Collection<MultipartFile> attachmentsAbstract;

    private Collection<Long> authors;

    @Override
    public boolean apply(Document object) {
        boolean modified = false;

        if (description != null) {
            object.setDescription(description);
            modified = true;
        }
        if (sourceId != null) {
            object.setSourceId(sourceId);
            modified = true;
        }
        if (doi != null) {
            object.setDoi(doi);
            modified = true;
        }
        if (publisher != null) {
            object.setPublisher(publisher);
            modified = true;
        }
        if (publicationIndex != null) {
            object.setPublicationIndex(publicationIndex);
            modified = true;
        }
        if (publishDate != null) {
            object.setPublishDate(publishDate);
            modified = true;
        }
        if (languageId != null) {
            object.setLanguageId(languageId);
            modified = true;
        }

        if (title != null) {
            object.setTitle(title);
            modified = true;
        }
        if (abstractText != null) {
            object.setAbstractText(abstractText);
            modified = true;
        }
        if (classificationId != null) {
            object.setClassificationId(classificationId);
            modified = true;
        }
        if (documentType != null) {
            object.setDocumentType(documentType);
            modified = true;
        }
        if (groupId != null) {
            object.setGroupId(groupId);
            modified = true;
        }

        if (majorId != null) {
            object.setMajorId(majorId);
            modified = true;
        }
        if (specializationId != null) {
            object.setSpecializationId(specializationId);
            modified = true;
        }
        if (citedNumber != null) {
            object.setCitedNumber(citedNumber);
            modified = true;
        }
        if (mtaJournalId != null) {
            object.setMtaJournalId(mtaJournalId);
            modified = true;
        }

        return modified;
    }
}
