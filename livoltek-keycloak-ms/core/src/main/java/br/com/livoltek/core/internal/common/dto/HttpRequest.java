package br.com.livoltek.core.internal.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class HttpRequest<T, S> {
        String url;
        T payload;
        @Setter
        S response;
        String username;
        String password;
        String bearerToken;
        @Setter
        String location;
        @Setter
        Integer statusCodeResponse;

    public HttpRequest(String url, T payload, String bearerToken) {
        this(url, payload);
        this.bearerToken = bearerToken;
    }

    public HttpRequest(String url, T payload) {
        this.url = url;
        this.payload = payload;
    }

    public HttpRequest(String url, T payload, String username, String password) {
        this.url = url;
        this.payload = payload;
        this.username = username;
        this.password = password;
    }
}