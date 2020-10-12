package vn.com.mta.science.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured/ws/rest/v1/")
public class StatsAPI {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
}
