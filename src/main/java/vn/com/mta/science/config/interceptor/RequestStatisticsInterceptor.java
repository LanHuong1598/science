package vn.com.mta.science.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestStatisticsInterceptor implements AsyncHandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestStatisticsInterceptor.class);

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private HibernateStatisticsInterceptor statisticsInterceptor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("Received-Time", System.currentTimeMillis());
        statisticsInterceptor.startCounter();

        if (!request.getMethod().toUpperCase().equals("GET")) {
            String authorization = request.getHeader("Authorization");
            logger.info("[REQUEST] {} {} | ReqID: {} | ReqAddr: {} | ReqTime: {}", request.getMethod(), request.getRequestURI(),
                    request.getHeader("Request-Id"), request.getRemoteAddr(), request.getHeader("Request-Time"));
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            long duration = System.currentTimeMillis() - (Long) request.getAttribute("Received-Time");
            Long queryCount = statisticsInterceptor.getQueryCount();
            statisticsInterceptor.clearCounter();

            response.setHeader("Request-Id", request.getHeader("Request-Id"));
            response.setHeader("Request-Time", request.getHeader("Request-Time"));
            response.setHeader("Corporate-Id", request.getHeader("Corporate-Id"));

            if (!request.getMethod().toUpperCase().equals("GET")) {
                logger.info("[RESPONSE] {} {} | ReqID: {} | Time: {} ms | Queries: {}", request.getMethod(), request.getRequestURI(), request.getHeader("Request-Id"), duration, queryCount);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //concurrent handling cannot be supported here
        statisticsInterceptor.clearCounter();
    }
}
