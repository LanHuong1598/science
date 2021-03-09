package vn.com.mta.science.module.service;

import org.apache.commons.collections4.ListUtils;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.repository.dao.PaginationInfo;
import vn.com.mta.science.module.model.*;
import vn.com.mta.science.module.schema.*;
import vn.com.mta.science.module.service.db.AffiliationDAO;
import vn.com.mta.science.module.service.filter.*;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.model.User;
import vn.com.mta.science.module.user.service.db.UserDAO;
import vn.com.mta.science.util.ItechAuthority;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.security.sasl.AuthenticationException;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Transactional
@Service("hospitalBookmarkService")
public class StaffBookmarkServiceImpl implements StaffBookmarkService {

    @Value("${app.image}")
    private String imagePath;

    public ocr getImage(MultipartFile image) throws APIException, IOException, InterruptedException {

        String uuid = UUID.randomUUID().toString();
        String path = "/home/lanhuong/Documents/ORC/MTA_OCR_2020/server/" + uuid;
        File attachment = new File(path + ".jpg");

        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(attachment));
        FileCopyUtils.copy(image.getInputStream(), stream);
        stream.close();


        ProcessBuilder processBuilder =
                new ProcessBuilder("sh", "-c", "cd /home/lanhuong/Documents/ORC/MTA_OCR_2020/ && python test.py " + path + ".jpg");
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        assert exitCode == 0;

        Thread.sleep(1000);
        File f = new File("/home/lanhuong/Documents/ORC/MTA_OCR_2020/result_image/" + uuid + ".jpg");
        String encodedString = Base64
                .getEncoder()
                .encodeToString(StreamUtils.copyToByteArray(new FileInputStream(f)));

        int i = 0;
        String idNo = "";
        String name = "";
        FileInputStream fis = new FileInputStream("/home/lanhuong/Documents/ORC/MTA_OCR_2020/result_image/" + uuid + ".txt");
        Scanner scanner = new Scanner(fis);

        while (scanner.hasNextLine()) {
            if (i == 0) idNo = scanner.nextLine();
            if (i == 1) name = scanner.nextLine();
            i++;
            if (i > 1) break;
        }

        scanner.close();

        ocr ocr = new ocr(encodedString, idNo, name);

        return ocr;


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

    @Autowired
    UserDAO userDAO;

    @Override
    public List<Menus> getDocumentTypeMenus() throws IOException {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setOrderAsc(true);
        paginationInfo.setOrderColumn("name");
        paginationInfo.setMaxResults(1000);
        paginationInfo.setFirstRow(0);

        List<DocumentTypeGet> res = new ArrayList<>() ; //= documentTypeService.getPageOfData(paginationInfo);
        res.add(documentTypeService.getById(1L));
        res.add(documentTypeService.getById(2L));
        res.add(documentTypeService.getById(10L));
        res.add(documentTypeService.getById(3L));
        res.add(documentTypeService.getById(4L));
        res.add(documentTypeService.getById(5L));
        res.add(documentTypeService.getById(6L));
        res.add(documentTypeService.getById(7L));
        res.add(documentTypeService.getById(8L));
        res.add(documentTypeService.getById(9L));
        res.add(documentTypeService.getById(11L));


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

        DocumentFilter documentFilter = new DocumentFilter();

        if (statsFilter.getType().equals("khoa")) {


            AffiliationFilter affiliationFilter = new AffiliationFilter();
            affiliationFilter.setParentId(Long.valueOf(statsFilter.getKeyword()));
            List<Affiliation> affiliations = affiliationDAO.getPageOfData(affiliationFilter, null);
            documentFilter.setAffiliationId(affiliations.stream().map(Affiliation::getId).collect(Collectors.toSet()));
        }

        if (statsFilter.getType().equals("bomon")) {


            Set<Long> aff = new HashSet<>();
            aff.add(Long.valueOf(statsFilter.getKeyword()));
            documentFilter.setAffiliationId(aff);
        }

        if (statsFilter.getType().equals("tacgia")) {
            Set<String> aff = new HashSet<>();
            aff.add(statsFilter.getKeyword());
            documentFilter.setAuthorId(aff);
        }

        if (statsFilter.getType().equals("ncm")) {

            Set<Long> aff = new HashSet<>();
            aff.add(Long.valueOf(statsFilter.getKeyword()));
            documentFilter.setGroupId(aff);
        }

        List<StatsByYear> list = new ArrayList<>();
        for (Long year = statsFilter.getStarttime(); year <= statsFilter.getEndtime(); year++) {
            StatsByYear statsByYear = new StatsByYear();
            statsByYear.setYear(year);
            documentFilter.setStarttime(year.toString());
            documentFilter.setEndtime(year.toString()+ "-99-99");

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
            inventionFilter.setAffiliationId(documentFilter.getAffiliationId());
            inventionFilter.setAuthorId(documentFilter.getAuthorId());
            inventionFilter.setClassificationId(documentFilter.getClassificationId());
            inventionFilter.setGroupId(documentFilter.getGroupId());
            inventionFilter.setKeyword(documentFilter.getKeyword());
            inventionFilter.setMajorId(documentFilter.getMajorId());
            inventionFilter.setSpecializationId(documentFilter.getSpecializationId());

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
                        total.getRBBTN() + total.getRBBTCKHKT(),
                total.getRBCQUOCTE().longValue() + total.getRBCSCOPUS().longValue() + total.getRBCTN().longValue(),
                total.getRSACHQTUOCTE().longValue() + total.getRSACHSCOPUS().longValue() + total.getRSACHTN().longValue(),
                total.getRTHUGUI().longValue() + total.getRTONGQUAN().longValue() + total.getRMOTA().longValue()
                        + total.getRBINHLUAN().longValue() + total.getRXALUAN().longValue() + total.getRDIEUTRA().longValue() +
                        total.getRDINHCHINH().longValue(),
                total.getRSHTTSANGCHE().longValue() + total.getRGIAIPHAP().longValue()));


        return s;
    }


    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Override
    public String getStatsFile(StatsFilter statsFilter) throws IOException {
        Stats stats = getStats(statsFilter);

        BasicConfigurator.configure();

        List<StatsByYear> list = stats.getDs();

        int targetSize = 5;
        List<List<StatsByYear>> output = ListUtils.partition(list, targetSize);

        Document document = new Document();

        for (List<StatsByYear> statsByYears : output) {
            File template = null;
//            if (statsByYears.size() == 5)
//                template = new File("/home/lanhuong/Downloads/science/baocao_template/template1_5years.docx");
//            else if (statsByYears.size() == 4)
//                template = new File("/home/lanhuong/Downloads/science/baocao_template/template1_4years.docx");
//            else if (statsByYears.size() == 3)
//                template = new File("/home/lanhuong/Downloads/science/baocao_template/template1_3years.docx");
//            else if (statsByYears.size() == 2)
//                template = new File("/home/lanhuong/Downloads/science/baocao_template/template1_2years.docx");
//            else if (statsByYears.size() == 1)
//                template = new File("/home/lanhuong/Downloads/science/baocao_template/template1_1years.docx");

            if (statsByYears.size() == 5)
                template = new File("E:\\Collection\\deploy\\backend\\baocao_template\\template1_5years.docx");
              else if (statsByYears.size() == 4)
                template = new File("E:\\Collection\\deploy\\backend\\baocao_template\\template1_4years.docx");
            else if (statsByYears.size() == 3)
                template = new File("E:\\Collection\\deploy\\backend\\baocao_template\\template1_3years.docx");
            else if (statsByYears.size() == 2)
                template = new File("E:\\Collection\\deploy\\backend\\baocao_template\\template1_2years.docx");
            else if (statsByYears.size() == 1)
                template = new File("E:\\Collection\\deploy\\backend\\baocao_template\\template1_1years.docx");

            OutputStream out = new FileOutputStream(statsByYears.get(0).getYear().toString());

            byte[] bytes = Files.readAllBytes(template.toPath());
            com.spire.doc.Document doc = new Document();
            doc.loadFromStream(new ByteArrayInputStream(bytes), FileFormat.Docx);

            // Set value
//        doc.replace(Pattern.compile("\\$\\{day}"), "09");
//        doc.replace(Pattern.compile("\\$\\{month}"), "11");
//        doc.replace(Pattern.compile("\\$\\{year}"), "2020");
//        doc.replace(Pattern.compile("\\$\\{year}"), "2015");
//        doc.replace(Pattern.compile("\\$\\{sum}"), "18");

            String date = dateFormat.format(java.util.Calendar.getInstance().getTime());

            doc.replace(Pattern.compile("day"), date.substring(6, 8));
            doc.replace(Pattern.compile("month"), date.substring(4, 6));
            doc.replace(Pattern.compile("year"), date.substring(0, 4));
            doc.replace(Pattern.compile("yr1"), statsFilter.getStarttime().toString());
            doc.replace(Pattern.compile("yr2"), statsFilter.getEndtime().toString());

            if (!statsFilter.getType().equals("tacgia")) {
                Affiliation affiliation = affiliationDAO.getById(Long.valueOf(statsFilter.getKeyword()));
                doc.replace(Pattern.compile("\\{TYPE}"), affiliation.getName().toUpperCase());
            }
            else{
                doc.replace(Pattern.compile("\\{TYPE}"), statsFilter.getKeyword().toUpperCase());
            }

            for (int i = Math.toIntExact(statsByYears.get(0).getYear()); i <= statsByYears.get(statsByYears.size() - 1).getYear(); i++) {
                doc.replace(Pattern.compile("\\{y_" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "}"), String.valueOf(i));
            }

            for (int i = Math.toIntExact(statsByYears.get(0).getYear()); i <= statsByYears.get(statsByYears.size() - 1).getYear(); i++) {
                StatsByYear statsByYear = statsByYears.get((int) (i - statsByYears.get(0).getYear()));
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "1}"), statsByYear.getRBBISI().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "2}"), statsByYear.getRBBSCOPUS().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "3}"), statsByYear.getRBBTCKHKT().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "4}"), statsByYear.getRBBICT().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "5}"), statsByYear.getRBBKTCTDB().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "6}"), statsByYear.getRBBBDKTQS().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "7}"), statsByYear.getRBBQUOCTE().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "8}"), statsByYear.getRBBTN().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "9}"), statsByYear.getRBCSCOPUS().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "10}"), statsByYear.getRBCQUOCTE().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "11}"), statsByYear.getRBCTN().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "12}"), statsByYear.getRSACHSCOPUS().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "13}"), statsByYear.getRSACHQTUOCTE().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "14}"), statsByYear.getRSACHTN().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "15}"), statsByYear.getRTHUGUI().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "16}"), statsByYear.getRTONGQUAN().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "17}"), statsByYear.getRMOTA().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "18}"), statsByYear.getRBINHLUAN().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "19}"), statsByYear.getRXALUAN().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "20}"), statsByYear.getRDINHCHINH().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "21}"), statsByYear.getRDIEUTRA().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "22}"), statsByYear.getRSHTTSANGCHE().toString());
                doc.replace(Pattern.compile("\\{r" + String.valueOf(i - statsByYears.get(0).getYear() + 1) + "23}"), statsByYear.getRGIAIPHAP().toString());
            }

            StatsByYear total = new StatsByYear();

            total.setRBBISI(statsByYears.stream().mapToInt(StatsByYear::getRBBISI).sum());
            total.setRBBSCOPUS(statsByYears.stream().mapToInt(StatsByYear::getRBBSCOPUS).sum());
            total.setRBBTCKHKT(statsByYears.stream().mapToInt(StatsByYear::getRBBTCKHKT).sum());
            total.setRBBTN(statsByYears.stream().mapToInt(StatsByYear::getRBBTN).sum());
            total.setRBBQUOCTE(statsByYears.stream().mapToInt(StatsByYear::getRBBQUOCTE).sum());
            total.setRBBICT(statsByYears.stream().mapToInt(StatsByYear::getRBBICT).sum());
            total.setRBBBDKTQS(statsByYears.stream().mapToInt(StatsByYear::getRBBBDKTQS).sum());
            total.setRBBKTCTDB(statsByYears.stream().mapToInt(StatsByYear::getRBBKTCTDB).sum());

            total.setRBCSCOPUS(statsByYears.stream().mapToInt(StatsByYear::getRBCSCOPUS).sum());
            total.setRBCQUOCTE(statsByYears.stream().mapToInt(StatsByYear::getRBCQUOCTE).sum());
            total.setRBCTN(statsByYears.stream().mapToInt(StatsByYear::getRBCTN).sum());

            total.setRSACHSCOPUS(statsByYears.stream().mapToInt(StatsByYear::getRSACHSCOPUS).sum());
            total.setRSACHQTUOCTE(statsByYears.stream().mapToInt(StatsByYear::getRSACHQTUOCTE).sum());
            total.setRSACHTN(statsByYears.stream().mapToInt(StatsByYear::getRSACHTN).sum());

            total.setRTHUGUI(statsByYears.stream().mapToInt(StatsByYear::getRTHUGUI).sum());
            total.setRTONGQUAN(statsByYears.stream().mapToInt(StatsByYear::getRTONGQUAN).sum());
            total.setRMOTA(statsByYears.stream().mapToInt(StatsByYear::getRMOTA).sum());
            total.setRBINHLUAN(statsByYears.stream().mapToInt(StatsByYear::getRBINHLUAN).sum());
            total.setRXALUAN(statsByYears.stream().mapToInt(StatsByYear::getRXALUAN).sum());
            total.setRDINHCHINH(statsByYears.stream().mapToInt(StatsByYear::getRDINHCHINH).sum());
            total.setRDIEUTRA(statsByYears.stream().mapToInt(StatsByYear::getRDIEUTRA).sum());
            total.setRSHTTSANGCHE(statsByYears.stream().mapToInt(StatsByYear::getRSHTTSANGCHE).sum());
            total.setRGIAIPHAP(statsByYears.stream().mapToInt(StatsByYear::getRGIAIPHAP).sum());

            doc.replace(Pattern.compile("\\{r6" + "1}"), total.getRBBISI().toString());
            doc.replace(Pattern.compile("\\{r6" + "2}"), total.getRBBSCOPUS().toString());
            doc.replace(Pattern.compile("\\{r6" + "3}"), total.getRBBTCKHKT().toString());
            doc.replace(Pattern.compile("\\{r6" + "4}"), total.getRBBICT().toString());
            doc.replace(Pattern.compile("\\{r6" + "5}"), total.getRBBKTCTDB().toString());
            doc.replace(Pattern.compile("\\{r6" + "6}"), total.getRBBBDKTQS().toString());
            doc.replace(Pattern.compile("\\{r6" + "7}"), total.getRBBQUOCTE().toString());
            doc.replace(Pattern.compile("\\{r6" + "8}"), total.getRBBTN().toString());
            doc.replace(Pattern.compile("\\{r6" + "9}"), total.getRBCSCOPUS().toString());
            doc.replace(Pattern.compile("\\{r6" + "10}"), total.getRBCQUOCTE().toString());
            doc.replace(Pattern.compile("\\{r6" + "11}"), total.getRBCTN().toString());
            doc.replace(Pattern.compile("\\{r6" + "12}"), total.getRSACHSCOPUS().toString());
            doc.replace(Pattern.compile("\\{r6" + "13}"), total.getRSACHQTUOCTE().toString());
            doc.replace(Pattern.compile("\\{r6" + "14}"), total.getRSACHTN().toString());
            doc.replace(Pattern.compile("\\{r6" + "15}"), total.getRTHUGUI().toString());
            doc.replace(Pattern.compile("\\{r6" + "16}"), total.getRTONGQUAN().toString());
            doc.replace(Pattern.compile("\\{r6" + "17}"), total.getRMOTA().toString());
            doc.replace(Pattern.compile("\\{r6" + "18}"), total.getRBINHLUAN().toString());
            doc.replace(Pattern.compile("\\{r6" + "19}"), total.getRXALUAN().toString());
            doc.replace(Pattern.compile("\\{r6" + "20}"), total.getRDINHCHINH().toString());
            doc.replace(Pattern.compile("\\{r6" + "21}"), total.getRDIEUTRA().toString());
            doc.replace(Pattern.compile("\\{r6" + "22}"), total.getRSHTTSANGCHE().toString());
            doc.replace(Pattern.compile("\\{r6" + "23}"), total.getRGIAIPHAP().toString());

            StatsTotal statsTotal = new StatsTotal(
                    total.getRBBISI().longValue() + total.getRBBSCOPUS().longValue() +
                            total.getRBBBDKTQS().longValue() + total.getRBBICT() +
                            total.getRBBKTCTDB().longValue() + total.getRBBQUOCTE() +
                            total.getRBBTN() + total.getRBBTCKHKT(),
                    total.getRBCQUOCTE().longValue() + total.getRBCSCOPUS().longValue() + total.getRBCTN().longValue(),
                    total.getRSACHQTUOCTE().longValue() + total.getRSACHSCOPUS().longValue() + total.getRSACHTN().longValue(),
                    total.getRTHUGUI().longValue() + total.getRTONGQUAN().longValue() + total.getRMOTA().longValue()
                            + total.getRBINHLUAN().longValue() + total.getRXALUAN().longValue() + total.getRDIEUTRA().longValue() +
                            total.getRDINHCHINH().longValue(),
                    total.getRSHTTSANGCHE().longValue() + total.getRGIAIPHAP().longValue());

            doc.replace(Pattern.compile("\\{r7" + "1}"), statsTotal.getRBB().toString());
            doc.replace(Pattern.compile("\\{r7" + "2}"), statsTotal.getRBC().toString());
            doc.replace(Pattern.compile("\\{r7" + "3}"), statsTotal.getRSACH().toString());
            doc.replace(Pattern.compile("\\{r7" + "4}"), statsTotal.getRKHAC().toString());
            doc.replace(Pattern.compile("\\{r7" + "5}"), statsTotal.getRSHTT().toString());

//        for (int r = 1; r <= 7; r++) {
//            for (int i = 1; i <= 23; i++) {
//                doc.replace(Pattern.compile("r" + String.valueOf(r) + String.valueOf(i)), String.valueOf(r) + String.valueOf(i));
//            }
//        }


            doc.replace(Pattern.compile("name1"), "");
            doc.replace(Pattern.compile("name2"), "");

//        doc.replace(Pattern.compile("\\$\\{name1}"), "Lan Hương");
//        doc.replace(Pattern.compile("\\$\\{name2}"), "Nguyễn Hoàng Vũ");


            doc.saveToFile(out, FileFormat.Doc);
            document.insertTextFromFile(statsByYears.get(0).getYear().toString(), FileFormat.Doc);
            File file = new File(statsByYears.get(0).getYear().toString());
            if (file.exists()) file.delete();

        }
        document.saveToFile("Output.doc", FileFormat.Doc);
        File file = new File("Output.doc");
        String encodedString = Base64
                .getEncoder()
                .encodeToString(StreamUtils.copyToByteArray(new FileInputStream(file)));
        return encodedString;
    }


    @Override
    public String getPDF(String type, Long id) throws APIException, IOException {

        if (!type.equals("invention")){
            DocumentGet document = documentService.getById(id);

            String base64File = "";
            File file = new File(document.getAttachmentsFullText().getUrl());
            try (FileInputStream imageInFile = new FileInputStream(file)) {
                // Reading a file from file system
                byte fileData[] = new byte[(int) file.length()];
                imageInFile.read(fileData);
                base64File = Base64.getEncoder().encodeToString(fileData);
            } catch (FileNotFoundException e) {
                System.out.println("File not found" + e);
            } catch (IOException ioe) {
                System.out.println("Exception while reading the file " + ioe);
            }
            return base64File;
        } else {
            InventionGet inventionGet = inventionService.getById(id);
            String base64File = "";
            File file = new File(inventionGet.getAttachmentsFullText().getUrl());
            try (FileInputStream imageInFile = new FileInputStream(file)) {
                // Reading a file from file system
                byte fileData[] = new byte[(int) file.length()];
                imageInFile.read(fileData);
                base64File = Base64.getEncoder().encodeToString(fileData);
            } catch (FileNotFoundException e) {
                System.out.println("File not found" + e);
            } catch (IOException ioe) {
                System.out.println("Exception while reading the file " + ioe);
            }
            return base64File;
        }
    }

    @Override
    public byte[] getPDFB(String type, Long id) throws APIException, IOException {
        DocumentGet document = documentService.getById(id);
        File file = new File("/home/lanhuong/Occupational change and wage inequality European Jobs Monitor 2017 - ef1710en.pdf");
        return StreamUtils.copyToByteArray(new FileInputStream(file));
    }


    @Override
    public void createSheet() throws Exception {

        System.out.println("Start");
        String to = "p8_levt@mta.edu.vn";
        // Sender's email ID needs to be mentioned
        String from = "p8_levt@mta.edu.vn";
        final String username = "p8_levt@mta.edu.vn";//change accordingly
        final String password = "30111996";//change accordingly
        // Assuming you are sending email through relay.jangosmtp.net
        Properties props = new Properties();
        props.put("mail.smtp.host", "86.8.24.30");
        props.put("mail.smtp.socketFactory.port", "80");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "80");
        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            // Set Subject: header field
            message.setSubject("Attachment");
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Now set the actual message
            messageBodyPart.setText("Please find the attachment below");
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "D:\\Deploy\\baocao_template\\template1_5years.docx";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);
            // Send message
            System.out.println("Send");
            Transport.send(message);
            System.out.println("Email Sent Successfully !!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
//
//        com.spire.doc.Document document = new Document("Don-de-nghi-Vu-sua-07.11.2020.docx");
//        document.insertTextFromFile("Don-de-nghi-Vu-sua-07.11.2020(1).docx", FileFormat.Docx_2013);
//        document.saveToFile("Output.docx", FileFormat.Docx_2013);
//
//        try {
//            FileInputStream inputStream = new FileInputStream(
//                    new File("/home/lanhuong/Downloads/science/JavaBooks.xlsx"));
//            Workbook workbook = WorkbookFactory.create(inputStream);
//
//            Sheet sheet = workbook.getSheetAt(0);
//            XSSFRow sheetrow = (XSSFRow) sheet.getRow(3);
//            if (sheetrow == null) {
//                sheetrow = (XSSFRow) sheet.createRow(3);
//            }
////Update the value of cell
//            Cell cell = null;
//            cell = sheetrow.getCell(3);
//            if (cell == null) {
//                cell = sheetrow.createCell(3);
//            }
//            cell.setCellValue("Pass");
//
//            inputStream.close();
//
//            FileOutputStream outputStream = new FileOutputStream("JavaBooks.xlsx");
//            workbook.write(outputStream);
//            workbook.close();
//            outputStream.close();
//
//        } catch (IOException | EncryptedDocumentException ex) {
//            ex.printStackTrace();
//        }
    }
}