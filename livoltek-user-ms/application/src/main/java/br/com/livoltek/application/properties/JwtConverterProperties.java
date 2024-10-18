package br.com.livoltek.application.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class JwtConverterProperties {

    private final String resourceId;
    private final String principalAttribute;
}