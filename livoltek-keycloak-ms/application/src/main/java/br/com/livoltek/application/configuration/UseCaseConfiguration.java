package br.com.livoltek.application.configuration;

import br.com.livoltek.core.api.common.service.HttpService;
import br.com.livoltek.core.api.properties.KeycloakProperties;
import br.com.livoltek.core.api.keycloak.usecase.CreateUserKeycloak;
import br.com.livoltek.core.api.keycloak.usecase.GetToken;
import br.com.livoltek.core.internal.common.usecase.expression.*;
import br.com.livoltek.core.api.common.usecase.expression.*;
import br.com.livoltek.core.internal.keycloak.usecase.CreateUserKeycloakDefault;
import br.com.livoltek.core.internal.keycloak.usecase.GetTokenDefault;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    GerarExpression gerarExpression(GerarPredicadoInteger gerarPredicadoInteger,
                                    GerarPredicadoLong gerarPredicadoLong,
                                    GerarPredicadoDecimal gerarPredicadoDecimal,
                                    GerarPredicadoFloat gerarPredicadoFloat,
                                    GerarPredicadoBoolean gerarPredicadoBoolean,
                                    GerarPredicadoString gerarPredicadoString,
                                    GerarPredicadoDate gerarPredicadoDate,
                                    GerarPredicadoDateTime gerarPredicateDateTime) {
        return new GerarExpressionDefault(gerarPredicadoInteger,
                gerarPredicadoLong,
                gerarPredicadoDecimal,
                gerarPredicadoFloat,
                gerarPredicadoBoolean,
                gerarPredicadoString,
                gerarPredicadoDate,
                gerarPredicateDateTime);
    }

    @Bean
    GerarPredicadoBoolean gerarPredicadoBoolean() {
        return new GerarPredicadoBooleanDefault();
    }

    @Bean
    GerarPredicadoDate gerarPredicadoDate() {
        return new GerarPredicadoDateDefault();
    }

    @Bean
    GerarPredicadoDateTime gerarPredicadoDateTime() {
        return new GerarPredicadoDateTimeDefault();
    }

    @Bean
    GerarPredicadoDecimal gerarPredicadoDecimal() {
        return new GerarPredicadoDecimalDefault();
    }

    @Bean
    GerarPredicadoFloat gerarPredicadoFloat() {
        return new GerarPredicadoFloatDefault();
    }

    @Bean
    GerarPredicadoInteger gerarPredicadoInteger() {
        return new GerarPredicadoIntegerDefault();
    }

    @Bean
    GerarPredicadoLong gerarPredicadoLong() {
        return new GerarPredicadoLongDefault();
    }

    @Bean
    GerarPredicadoString gerarPredicadoString() {
        return new GerarPredicadoStringDefault();
    }

    @Bean
    GetToken getToken(KeycloakProperties keycloakProperties,
                      HttpService httpService) {
        return new GetTokenDefault(keycloakProperties, httpService);
    }

    @Bean
    CreateUserKeycloak createUserKeycloak(KeycloakProperties keycloakProperties,
                                          GetToken getToken,
                                          HttpService httpService) {
        return new CreateUserKeycloakDefault(keycloakProperties, getToken, httpService);
    }
}