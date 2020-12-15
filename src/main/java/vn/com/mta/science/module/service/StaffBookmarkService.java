package vn.com.mta.science.module.service;

import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.mta.science.module.model.Menus;
import vn.com.mta.science.module.schema.Stats;
import vn.com.mta.science.module.schema.ocr;
import vn.com.mta.science.module.service.filter.StatsFilter;

import java.io.IOException;
import java.util.List;

public interface StaffBookmarkService {

    public ocr getImage(MultipartFile image) throws APIException, IOException, InterruptedException;

    public List<Menus> getDocumentTypeMenus() throws IOException;

    public Stats getStats(StatsFilter statsFilter) throws IOException;

    public String getPDF(String type, Long id) throws APIException, IOException, InterruptedException;

    public void createSheet() throws Exception;

    public String getStatsFile(StatsFilter statsFilter) throws IOException ;

    public byte[] getPDFB(String type, Long id) throws APIException, IOException;

}
