package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.interfaces.BaseEntity;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaUpdate;
import vn.com.mta.science.module.model.Invention;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class InventionUpdate extends SchemaUpdate<Invention, Long> {

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

    @Override
    public boolean apply(Invention object) {
        boolean modified = false;

        if (description != null) {
            object.setDescription(description);
            modified = true;
        }
        if (name != null) {
            object.setName(name);
            modified = true;
        }
        if (applicationNo != null) {
            object.setApplicationNo(applicationNo);
            modified = true;
        }
        if (fillingDate != null) {
            object.setFillingDate(fillingDate);
            modified = true;
        }
        if (decidedId != null) {
            object.setDecidedId(decidedId);
            modified = true;
        }
        if (publicationNo != null) {
            object.setPublicationNo(publicationNo);
            modified = true;
        }
        if (publicationDate != null) {
            object.setPublicationDate(publicationDate);
            modified = true;
        }

        if (ptcRegistrationDate != null) {
            object.setPtcRegistrationDate(ptcRegistrationDate);
            modified = true;
        }
        if (ptcExpiredDate != null) {
            object.setPtcExpiredDate(ptcExpiredDate);
            modified = true;
        }
        if (ipcId != null) {
            object.setIpcId(ipcId);
            modified = true;
        }
        if (source != null) {
            object.setSource(source);
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
        if (link != null) {
            object.setLink(link);
            modified = true;
        }
        if (owner != null) {
            object.setOwner(owner);
            modified = true;
        }
        if (ownerAddress != null) {
            object.setOwnerAddress(ownerAddress);
            modified = true;
        }

        if (type != null) {
            object.setType(type);
            modified = true;
        }

        return modified;
    }
}
