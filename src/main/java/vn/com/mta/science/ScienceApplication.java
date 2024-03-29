package vn.com.mta.science;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import vn.com.mta.science.config.interceptor.HibernateStatisticsInterceptor;

import javax.sql.DataSource;

@EnableCaching // Add this
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class ScienceApplication {

    public static void main(String[] args) throws Exception {

//		StaffBookmarkServiceImpl service = new StaffBookmarkServiceImpl();
//		service.createSheet(args);

        SpringApplication.run(ScienceApplication.class, args);

    }

    @Autowired
    protected DataSource dataSource;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setEntityInterceptor(statisticsInterceptor());
        sessionFactory.setPackagesToScan("vn.com.mta.science");
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public HibernateStatisticsInterceptor statisticsInterceptor() {
        return new HibernateStatisticsInterceptor();
    }
}
