package br.com.livoltek.application.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"br.com.livoltek.application.infrastructure.repository.livoltek"})
public class LivoltekDataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.livoltek")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.livoltek.jpa")
    JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    private Map<String, Object> generateHibernateProperties() {
        JpaProperties jpaProperties = jpaProperties();
        HashMap<String, Object> props = new HashMap<>();
        props.put("hibernate.show_sql", jpaProperties.getProperties().get(("show-sql")));
        props.put("hibernate.format_sql", jpaProperties.getProperties().get(("format-sql")));
        props.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        props.put("hibernate.default_schema", jpaProperties.getProperties().get(("default-schema")));
        return props;
    }

    @Bean
    @Primary
    DataSource dataSource() {
        DataSourceProperties properties = dataSourceProperties();
        System.out.println("Datasource URL: " + properties.getUrl());
        return properties.initializeDataSourceBuilder().build();
    }


    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("br.com.livoltek.core.internal.common.entity")
                .persistenceUnit("livoltek")
                .properties(generateHibernateProperties())
                .build();
    }

    @Bean
    @Primary
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}