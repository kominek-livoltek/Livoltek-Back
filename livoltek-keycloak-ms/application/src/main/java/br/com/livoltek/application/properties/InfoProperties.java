package br.com.livoltek.application.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "info.application")
public class InfoProperties {

    private final String name;
    private final String apiVersion;
    private final String apiDate;
    private final String uiVersion;
    private final String uiDate;
    private final String ambiente;
}
