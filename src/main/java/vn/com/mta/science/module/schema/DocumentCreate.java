package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.Document;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class DocumentCreate extends GeneratedIDSchemaCreate<Document> {

    private String description;

    private Long sourceId;

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

    private Integer citedNumber;

    private Collection<String> keyword;

    private Integer mtaJournalId;

    private Collection<MultipartFile> attachmentsFullText;

    private Collection<MultipartFile> attachmentsAbstract;

    private Collection<Long> authors;

    @Override
    public Document toEntry() {
        Document document = new Document();
        document.setDescription(description);
        document.setAbstractText(abstractText);
        document.setCitedNumber(citedNumber);
        document.setClassificationId(classificationId);
        document.setDocumentType(documentType);
        document.setDoi(doi);
        document.setGroupId(groupId);
        document.setLanguageId(languageId);
        document.setMajorId(majorId);
        document.setMtaJournalId(mtaJournalId);
        document.setPublicationIndex(publicationIndex);
        document.setPublishDate(publishDate);
        document.setPublisher(publisher);
        document.setSourceId(sourceId);
        document.setSpecializationId(specializationId);
        document.setTitle(title);

        return document;
    }
}
