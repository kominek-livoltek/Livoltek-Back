package br.com.livoltek.application.configuration;

import br.com.livoltek.core.api.common.service.HttpService;
import br.com.livoltek.core.api.keycloak.usecase.CreateKeycloakUser;
import br.com.livoltek.core.api.keycloak.usecase.GetKeycloakToken;
import br.com.livoltek.core.api.keycloak.usecase.SearchKeycloakUser;
import br.com.livoltek.core.api.properties.KeycloakProperties;
import br.com.livoltek.core.api.user.repository.UserRepository;
import br.com.livoltek.core.api.user.usecase.CreateUser;
import br.com.livoltek.core.api.user.usecase.UserEntityToResponse;
import br.com.livoltek.core.internal.common.usecase.expression.*;
import br.com.livoltek.core.api.common.usecase.expression.*;
import br.com.livoltek.core.internal.keycloak.usecase.CreateKeycloakUserDefault;
import br.com.livoltek.core.internal.keycloak.usecase.GetKeycloakKeycloakTokenDefault;
import br.com.livoltek.core.internal.keycloak.usecase.SearchKeycloakUserDefault;
import br.com.livoltek.core.internal.user.usecase.CreateUserDefault;
import br.com.livoltek.core.internal.user.usecase.UserEntityToResponseDefault;
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
    CreateUser createUser(UserRepository userRepository, SearchKeycloakUser searchKeycloakUser,
                          CreateKeycloakUser createKeycloakUser, UserEntityToResponse userEntityToResponse) {
        return new CreateUserDefault(userRepository, searchKeycloakUser, createKeycloakUser, userEntityToResponse);
    }

    @Bean
    SearchKeycloakUser searchKeycloakUser(KeycloakProperties keycloakProperties,
                                          GetKeycloakToken getKeycloakToken,
                                          HttpService httpService) {
        return new SearchKeycloakUserDefault(keycloakProperties, getKeycloakToken, httpService);
    }

    @Bean
    GetKeycloakToken getKeycloakToken(KeycloakProperties keycloakProperties, HttpService httpService) {
        return new GetKeycloakKeycloakTokenDefault(keycloakProperties, httpService);
    }

    @Bean
    CreateKeycloakUser createKeycloakUser(KeycloakProperties keycloakProperties,
                                          GetKeycloakToken getKeycloakToken,
                                          HttpService httpService) {
        return new CreateKeycloakUserDefault(keycloakProperties, getKeycloakToken, httpService);
    }

    @Bean
    UserEntityToResponse userEntityToResponse() {
        return new UserEntityToResponseDefault();
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
//        return rabbitTemplate;
//    }

//    @Bean
//    public MessageConverter producerJackson2MessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
}