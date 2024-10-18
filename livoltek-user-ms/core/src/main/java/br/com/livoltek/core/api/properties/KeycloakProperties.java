package br.com.livoltek.core.api.properties;

public interface KeycloakProperties {

    String grantType();

    String clientId();

    String clientSecret();

    String urlTokenClientSecret();

    String urlEndPointUsers();

    String urlEndpointAddUserRole();

    String defaultPassword();

}
