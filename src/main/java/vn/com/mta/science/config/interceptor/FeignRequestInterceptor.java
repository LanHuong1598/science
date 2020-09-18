package vn.com.mta.science.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(FeignRequestInterceptor.class);

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        if (logger.isDebugEnabled()) {
            logger.debug("[feign] request {} to {}", requestTemplate.method(), requestTemplate.url());
            logger.debug("[feign] enhanced header: {}", requestTemplate.headers());
            logger.debug(requestTemplate.toString());
        }

    }
}