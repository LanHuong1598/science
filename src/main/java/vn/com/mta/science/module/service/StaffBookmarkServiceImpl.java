package vn.com.mta.science.module.service;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.mapreduce.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.repository.dao.PaginationInfo;
import vn.com.itechcorp.base.util.UuidUtil;
import vn.com.mta.science.module.model.*;
import vn.com.mta.science.module.schema.*;
import vn.com.mta.science.module.service.db.AffiliationDAO;
import vn.com.mta.science.module.service.filter.*;


import javax.imageio.ImageIO;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Transactional
@Service("hospitalBookmarkService")
public class StaffBookmarkServiceImpl implements StaffBookmarkService {

    @Value("${app.image}")
    private String imagePath;

    public String getImage(MultipartFile image) throws APIException, IOException, InterruptedException {
//        Runtime r = Runtime.getRuntime();
//        String line = "cd /home/lanhuong/";
//        CommandLine cmdLine = CommandLine.parse(line);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
//
//        DefaultExecutor executor = new DefaultExecutor();
//        executor.setStreamHandler(streamHandler);
//
//        int exitCode = executor.execute(cmdLine);

//        return  outputStream.toByteArray();
        String type = image.getOriginalFilename().split("\\.")[1];

        File attachment = new File("input");

        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(attachment));
        FileCopyUtils.copy(image.getInputStream(), stream);
        stream.close();


        ProcessBuilder pb = new ProcessBuilder("sh", "-c", "conda", "activate", "tfmax");
        Process p1 = Runtime.getRuntime().exec("/bin/sh -c conda activate tfmax");
        Process p = pb.start();

        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "  cd /home/lanhuong/OCR/id & conda activate tfmax &   python /home/lanhuong/OCR/id/id-detector.py '/home/lanhuong/OCR/id/input'");
//        processBuilder.redirectErrorStream(true);
        File file = new File("test.txt");

        ProcessBuilder.Redirect.to(file);
        Process process = processBuilder.start();

        TimeUnit.SECONDS.sleep(5);
        File f = new File("/home/lanhuong/OCR/id/result.png");
        String encodedString = Base64
                .getEncoder()
                .encodeToString(StreamUtils.copyToByteArray(new FileInputStream(f)));
        return encodedString;


//        String command = " cd /home/lanhuong/OCR/id ; conda activate tfmax;  python id-detector.py '/home/lanhuong/OCR/id/test_set/2b395a1180091adc087fe2fba491f920.jpeg'";
//        try {
//            Process process = Runtime.getRuntime().exec(command);
//            process.waitFor();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }

//        return null;
    }

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    InventionService inventionService;

    @Autowired
    DocumentService documentService;

    @Autowired
    AffiliationDAO affiliationDAO;

    @Override
    public List<Menus> getDocumentTypeMenus() throws IOException {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setOrderAsc(true);
        paginationInfo.setOrderColumn("id");
        paginationInfo.setMaxResults(1000);
        paginationInfo.setFirstRow(0);

        List<DocumentTypeGet> res = documentTypeService.getPageOfData(paginationInfo);
        List<Menus> out = new ArrayList<>();
        for (DocumentTypeGet dc : res) {
            if (dc.getName().equals("--")) break;
            Menus menus = new Menus();
            menus.setId(dc.getId());
            menus.setTitle(dc.getName());
            Random rd = new Random();

            DocumentFilter documentFilter = new DocumentFilter();
            Set<Long> type = new HashSet<>();
            type.add(dc.getId());
            documentFilter.setDocumentType(type);
            int number = (int) documentService.getCountAll(documentFilter);

            menus.setCount((long) number);
            File f = new File(imagePath + dc.getId().toString() + ".png");
            String encodedString = Base64
                    .getEncoder()
                    .encodeToString(StreamUtils.copyToByteArray(new FileInputStream(f)));
            menus.setImg(encodedString);
            out.add(menus);
        }
        Menus menus = new Menus();
        menus.setId(12L);
        menus.setTitle("Sở hữu trí tuệ (Invention)");
        Random rd = new Random();
        int number = (int) inventionService.getCountAll();
        menus.setCount((long) number);
        File f = new File(imagePath + "12.png");
        String encodedString = Base64
                .getEncoder()
                .encodeToString(StreamUtils.copyToByteArray(new FileInputStream(f)));
        menus.setImg(encodedString);
        out.add(menus);


        return out;
    }

    @Override
    public Stats getStats(StatsFilter statsFilter) throws IOException {

        Stats s = new Stats();
        if (statsFilter.getType().equals("khoa")) {

            DocumentFilter documentFilter = new DocumentFilter();
            AffiliationFilter affiliationFilter = new AffiliationFilter();
            affiliationFilter.setParentId(statsFilter.getKeyword());
            List<Affiliation> affiliations = affiliationDAO.getPageOfData(statsFilter, null);
            documentFilter.setAffiliationId(affiliations.stream().map(Affiliation::getId).collect(Collectors.toSet()));

            List<StatsByYear> list = new ArrayList<>();
            for (Long year = statsFilter.getStarttime(); year <= statsFilter.getEndtime(); year++) {
                StatsByYear statsByYear = new StatsByYear();
                statsByYear.setYear(year);
                documentFilter.setStarttime(year.toString());
                documentFilter.setEndtime(year.toString());

                // BBKH - ISI
                Set<Long> type = new HashSet<>();
                type.add(1L);
                Set<Long> classification = new HashSet<>();
                classification.add(1L);
                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(classification);
                Long r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRBBISI(Math.toIntExact(r1));

                // BBKH - SCOPUS
                classification.clear();
                classification.add(2L);
                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(classification);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRBBSCOPUS(Math.toIntExact(r1));

                //BB TCKHKT
                classification.clear();
                classification.add(4L);
                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(classification);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRBBTCKHKT(Math.toIntExact(r1));

                statsByYear.setRBBICT(0);
                statsByYear.setRBBKTCTDB(0);
                statsByYear.setRBBBDKTQS(0);

                // BB - QT khac
                classification.clear();
                classification.add(3L);
                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(classification);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRBBQUOCTE(Math.toIntExact(r1));

                // Trong nuoc khac
                classification.clear();
                classification.add(5L);
                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(classification);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRBBTN(Math.toIntExact(r1));

                // BCHN  - scopus
                type.clear();
                type.add(10L);

                classification.clear();
                classification.add(2L);
                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(classification);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRBCSCOPUS(Math.toIntExact(r1));

                // BCHN - QT

                classification.clear();
                classification.add(3L);
                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(classification);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRBCQUOCTE(Math.toIntExact(r1));

                // BCHN - TN
                classification.clear();
                classification.add(5L);
                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(classification);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRBCTN(Math.toIntExact(r1));

                // Sach
                type.clear();
                type.add(3L);
                type.add(4L);

                classification.clear();
                classification.add(2L);
                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(classification);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRSACHSCOPUS(Math.toIntExact(r1));

                // sach = qt
                classification.clear();
                classification.add(3L);
                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(classification);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRSACHQTUOCTE(Math.toIntExact(r1));

                // sach - tn
                classification.clear();
                classification.add(5L);
                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(classification);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRSACHTN(Math.toIntExact(r1));

                // thu gui
                type.clear();
                type.add(11L);

                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(null);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRTHUGUI(Math.toIntExact(r1));


                // bb tong quan
                type.clear();
                type.add(2L);

                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(null);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRTONGQUAN(Math.toIntExact(r1));

                // bb mo ta
                type.clear();
                type.add(5L);

                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(null);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRMOTA(Math.toIntExact(r1));

                // binh luan
                type.clear();
                type.add(6L);

                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(null);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRBINHLUAN(Math.toIntExact(r1));

                // xa luan
                type.clear();
                type.add(7L);

                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(null);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRXALUAN(Math.toIntExact(r1));

                // dinh chinh
                type.clear();
                type.add(8L);

                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(null);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRDINHCHINH(Math.toIntExact(r1));

                // dieu tra
                type.clear();
                type.add(9L);

                documentFilter.setDocumentType(type);
                documentFilter.setClassificationId(null);
                r1 = documentService.getCountAll(documentFilter);
                statsByYear.setRDIEUTRA(Math.toIntExact(r1));

                // giai phap huu ich
                InventionFilter inventionFilter = new InventionFilter();
                inventionFilter.setType("Giải pháp hữu ích");
                inventionFilter.setEndtime(year.toString());
                inventionFilter.setStarttime(year.toString());

                r1 = inventionService.getCountAll(inventionFilter);
                statsByYear.setRGIAIPHAP(Math.toIntExact(r1));

                //
                inventionFilter.setType("Sáng chế");
                r1 = inventionService.getCountAll(inventionFilter);
                statsByYear.setRSHTTSANGCHE(Math.toIntExact(r1));

                list.add(statsByYear);
            }

            StatsByYear total = new StatsByYear();

            total.setRBBISI(list.stream().mapToInt(StatsByYear::getRBBISI).sum());
            total.setRBBSCOPUS(list.stream().mapToInt(StatsByYear::getRBBSCOPUS).sum());
            total.setRBBTCKHKT(list.stream().mapToInt(StatsByYear::getRBBTCKHKT).sum());
            total.setRBBTN(list.stream().mapToInt(StatsByYear::getRBBTN).sum());
            total.setRBBQUOCTE(list.stream().mapToInt(StatsByYear::getRBBQUOCTE).sum());
            total.setRBBICT(list.stream().mapToInt(StatsByYear::getRBBICT).sum());
            total.setRBBBDKTQS(list.stream().mapToInt(StatsByYear::getRBBBDKTQS).sum());
            total.setRBBKTCTDB(list.stream().mapToInt(StatsByYear::getRBBKTCTDB).sum());

            total.setRBCSCOPUS(list.stream().mapToInt(StatsByYear::getRBCSCOPUS).sum());
            total.setRBCQUOCTE(list.stream().mapToInt(StatsByYear::getRBCQUOCTE).sum());
            total.setRBCTN(list.stream().mapToInt(StatsByYear::getRBCTN).sum());

            total.setRSACHSCOPUS(list.stream().mapToInt(StatsByYear::getRSACHSCOPUS).sum());
            total.setRSACHQTUOCTE(list.stream().mapToInt(StatsByYear::getRSACHQTUOCTE).sum());
            total.setRSACHTN(list.stream().mapToInt(StatsByYear::getRSACHTN).sum());

            total.setRTHUGUI(list.stream().mapToInt(StatsByYear::getRTHUGUI).sum());
            total.setRTONGQUAN(list.stream().mapToInt(StatsByYear::getRTONGQUAN).sum());
            total.setRMOTA(list.stream().mapToInt(StatsByYear::getRMOTA).sum());
            total.setRBINHLUAN(list.stream().mapToInt(StatsByYear::getRBINHLUAN).sum());
            total.setRXALUAN(list.stream().mapToInt(StatsByYear::getRXALUAN).sum());
            total.setRDINHCHINH(list.stream().mapToInt(StatsByYear::getRDINHCHINH).sum());
            total.setRDIEUTRA(list.stream().mapToInt(StatsByYear::getRDIEUTRA).sum());
            total.setRSHTTSANGCHE(list.stream().mapToInt(StatsByYear::getRSHTTSANGCHE).sum());
            total.setRGIAIPHAP(list.stream().mapToInt(StatsByYear::getRGIAIPHAP).sum());

            s.setDs(list);
            s.setTong(total);
            s.setTongso(new StatsTotal(
                    total.getRBBISI().longValue() + total.getRBBSCOPUS().longValue() +
                            total.getRBBBDKTQS().longValue() + total.getRBBICT() +
                            total.getRBBKTCTDB().longValue() + total.getRBBQUOCTE() +
                            total.getRBBSCOPUS() + total.getRBBTCKHKT(),
                    total.getRBCQUOCTE().longValue() + total.getRBCSCOPUS().longValue() + total.getRBCTN().longValue(),
                    total.getRSACHQTUOCTE().longValue() + total.getRSACHSCOPUS().longValue() + total.getRSACHTN().longValue(),
                    total.getRTHUGUI().longValue() + total.getRTONGQUAN().longValue() + total.getRMOTA().longValue()
                            + total.getRBINHLUAN().longValue() + total.getRXALUAN().longValue() + total.getRDIEUTRA().longValue() +
                            total.getRDINHCHINH().longValue(),
                    total.getRSHTTSANGCHE().longValue()+total.getRGIAIPHAP().longValue()));
        }


        return s;
    }

    @Override
    public String getPDF(String type, Long id) throws APIException, IOException {
        File file = new File("/home/lanhuong/Downloads/cmp_output_no_longer_signed.pdf");
        String encodedString = Base64
                .getEncoder()
                .encodeToString(StreamUtils.copyToByteArray(new FileInputStream(file)));
        return encodedString;
    }

}
