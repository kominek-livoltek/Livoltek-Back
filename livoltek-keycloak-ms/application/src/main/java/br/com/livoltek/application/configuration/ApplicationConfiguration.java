package br.com.livoltek.application.configuration;

import br.com.livoltek.application.infrastructure.service.exception.handler.RestTemplateResponseErrorHandler;
import br.com.livoltek.application.interceptor.LoggingInterceptor;
import br.com.livoltek.application.properties.InfoProperties;
import br.com.livoltek.application.properties.JwtConverterProperties;
import br.com.livoltek.application.properties.KeycloakPropertiesDefault;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
@EnableConfigurationProperties({
        KeycloakPropertiesDefault.class,
        InfoProperties.class,
        JwtConverterProperties.class
})
public class ApplicationConfiguration {

    @Bean
    Executor asyncExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingInterceptor());
        restTemplate.setInterceptors(interceptors);
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restTemplate;
    }

    @Bean
    JavaPropsMapper javaPropsMapper() {
        JavaPropsMapper javaPropsMapper = new JavaPropsMapper();
        javaPropsMapper.registerModule(new JavaTimeModule());
        javaPropsMapper.registerModule(new Hibernate6Module());
        return javaPropsMapper;
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Hibernate6Module())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}