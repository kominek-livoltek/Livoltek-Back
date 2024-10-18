package br.com.livoltek.application.resources;

import br.com.livoltek.application.properties.InfoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("application")
@RequiredArgsConstructor
public class ApplicationResource {

    private final InfoProperties infoProperties;

    @GetMapping
    InfoProperties infoProperties() {
        return infoProperties;
    }
}
