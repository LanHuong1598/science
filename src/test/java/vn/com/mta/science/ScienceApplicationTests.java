//package vn.com.mta.science;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.*;
//import java.io.IOException;
//
//@SpringBootTest
//class ScienceApplicationTests {
//
//	@Test
//	void contextLoads() throws IOException, InterruptedException {
//        ProcessBuilder processBuilder = new ProcessBuilder("python",
//                resolvePythonScriptPath("hello.py"));
//        processBuilder.redirectErrorStream(true);
//
//        Process process = processBuilder.start();
//        List<String> results = readProcessOutput(process.getInputStream());
//
//
//        int exitCode = process.waitFor();
//	}
//
//}
