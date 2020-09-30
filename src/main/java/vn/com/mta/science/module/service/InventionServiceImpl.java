package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.itechcorp.base.util.UuidUtil;
import vn.com.itechcorp.telerad.file.io.FileUtil;
import vn.com.mta.science.module.model.*;
import vn.com.mta.science.module.schema.*;
import vn.com.mta.science.module.service.db.*;
import vn.com.mta.science.module.service.filter.AttachmentFilter;
import vn.com.mta.science.module.service.filter.CitedFilter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service("inventionService")
public class InventionServiceImpl extends VoidableGeneratedIDSchemaServiceImpl<InventionGet, Invention> implements InventionService {

    @Autowired
    private InventionDAO inventionDAO;

    @Autowired
    private InventionMemberDAO inventionMemberDAO;

    @Override
    public InventionDAO getDAO() {
        return inventionDAO;
    }

    @Override
    public InventionGet convert(Invention invention) {
        return new InventionGet(invention);
    }

    @Override
    public InventionGet create(GeneratedIDSchemaCreate<Invention> entity, Long callerId) throws APIException {
        InventionCreate object = (InventionCreate) entity;

        Invention invention = inventionDAO.create(object.toEntry(), 0L);
        if (object.getAuthors() != null && !object.getAuthors().isEmpty()){
            for (Long id :object.getAuthors()){
                InventionMember inventionMember = new InventionMember();
                inventionMember.setAuthor_id(id);
                inventionMember.setInvention_id(invention.getId());
                inventionMemberDAO.create(inventionMember, 0L);
            }
        }

        InventionGet inventionGet = convert(invention);
        return inventionGet;
    }

}
