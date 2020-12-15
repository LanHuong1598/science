package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.AttachmentInvention;
import vn.com.mta.science.module.model.Invention;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class InventionGet extends SchemaGet<Invention, Long> {

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

    private AttachmentInvention attachmentsFullText;

    private String url;

    public InventionGet(Invention invention) {
        super(invention);
    }

    @Override
    public void parse(Invention invention) {
        applicationNo = invention.getApplicationNo();
        decidedId = invention.getDecidedId();
        description = invention.getDescription();
        fillingDate = invention.getFillingDate();
        groupId = invention.getGroupId();
        ipcId = invention.getIpcId();
        link = invention.getLink();
        majorId =invention.getMajorId();
        name = invention.getName();
        owner = invention.getOwner();
        ownerAddress = invention.getOwnerAddress();
        ptcExpiredDate = invention.getPtcExpiredDate();
        ptcRegistrationDate = invention.getPtcRegistrationDate();
        publicationDate = invention.getPublicationDate();
        publicationNo = invention.getPublicationNo();
        source = invention.getSource();
        type = invention.getType();
    }
}
