package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.Invention;

import javax.persistence.Column;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class InventionCreate extends GeneratedIDSchemaCreate<Invention> {

    private String name;

    private String description;

    private String applicationNo;

    private String fillingDate;

    private String decidedId;

    private String publicationNo;

    private String publicationDate;

    private String ptcRegistrationDate;

    private String ptcExpiredDate;

    private String ipcId;

    private String source;

    private String link;

    private String owner;

    private String ownerAddress;

    private Long majorId;

    private Long groupId;

    private String type;

    private Collection<Long> authors;

    private MultipartFile image;

    @Override
    public Invention toEntry() {
        Invention object = new Invention();
        object.setApplicationNo(applicationNo);
        object.setDecidedId(decidedId);
        object.setDescription(description);
        object.setFillingDate(fillingDate);
        object.setGroupId(groupId);
        object.setIpcId(ipcId);
        object.setLink(link);
        object.setMajorId(majorId);
        object.setName(name);
        object.setOwner(owner);
        object.setOwnerAddress(ownerAddress);
        object.setPtcExpiredDate(ptcExpiredDate);
        object.setPtcRegistrationDate(ptcRegistrationDate);
        object.setPublicationDate(publicationDate);
        object.setPublicationNo(publicationNo);
        object.setSource(source);
        object.setType(type);
        return object;
    }
}
