package br.com.livoltek.application.properties;

import br.com.livoltek.core.api.properties.KeycloakProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public class KeycloakPropertiesDefault implements KeycloakProperties {

    private final String grantType;
    private final String clientId;
    private final String clientSecret;
    private final String urlTokenClientSecret;
    private final String urlEndPointUsers;
    private final String urlEndpointAddUserRole;
    private final String defaultPassword;

    public KeycloakPropertiesDefault(
            String grantType,
            String clientId,
            String clientSecret,
            String urlTokenClientSecret,
            String urlEndPointUsers,
            String urlEndpointAddUserRole,
            String defaultPassword
    ) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.urlTokenClientSecret = urlTokenClientSecret;
        this.urlEndPointUsers = urlEndPointUsers;
        this.urlEndpointAddUserRole = urlEndpointAddUserRole;
        this.defaultPassword = defaultPassword;
    }
    @Override
    public String grantType() {
        return this.grantType;
    }

    @Override
    public String clientId() {
        return this.clientId;
    }

    @Override
    public String clientSecret() {
        return this.clientSecret;
    }
    @Override
    public String urlTokenClientSecret() {return this.urlTokenClientSecret;}

    @Override
    public String urlEndPointUsers() {
        return this.urlEndPointUsers;
    }

    @Override
    public String urlEndpointAddUserRole() {
        return this.urlEndpointAddUserRole;
    }

    @Override
    public String defaultPassword() { return this.defaultPassword; }

}
