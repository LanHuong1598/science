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

    List<Menus> getDocumentTypeMenus() throws IOException;

    Stats getStats(StatsFilter statsFilter, Long userId) throws IOException;

    String getPDF(String type, String id) throws APIException, IOException, InterruptedException;

    void createSheet() throws Exception;

    String getStatsFile(StatsFilter statsFilter, Long userId) throws IOException;

    byte[] getPDFB(String type, String id) throws APIException, IOException;

}
