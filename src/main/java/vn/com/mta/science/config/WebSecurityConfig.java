package vn.com.mta.science.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import vn.com.mta.science.module.user.auth.ItechUserDetailsService;
import vn.com.mta.science.module.user.auth.ItechUserPasswordEncoder;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ItechUserDetailsService itechUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(itechUserDetailsService).passwordEncoder(ItechUserPasswordEncoder.getInstance().getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/secured/ws/rest/v1/stats", "/secured/ws/rest/v1/login", "/secured/ws/rest/v1/api/download ", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**", "/secured/ws/rest/v1/test"
                        , "/secured/ws/rest/v1/download/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-user-token", "X-Total-Count",
                "Request-Id", "Request-Time", "Corporate-Id", "User-Id", "upload-id", "upload-key", "compression-type", "accept"));
        configuration.setExposedHeaders(Arrays.asList("x-user-token", "X-Total-Count"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}