package br.com.livoltek.core.internal.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Filter {

    private String key;
    private String operator;
    private String value;
}
