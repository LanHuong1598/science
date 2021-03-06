package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.mta.science.module.model.Attachment;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentGet implements Serializable {
    private long id;

    private String contentType;

    private long contentSizeKb;

    private String url;

    public AttachmentGet(Attachment att) {
        id = att.getId();
        contentType = att.getContentType();
        contentSizeKb = att.getContentSizeKb();
        url = att.getUrl();
    }
}
