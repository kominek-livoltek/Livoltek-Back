package br.com.livoltek.application.infrastructure.service;

import br.com.livoltek.core.api.common.service.HttpService;
import br.com.livoltek.core.internal.common.dto.HttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HttpServiceDefault implements HttpService {

    private final RestTemplate restTemplate;

    @Override
    public <T, S> HttpRequest<T, S> post(HttpRequest<T, S> request, Class<S> responseType) {
        return this.exchange(request, responseType, MediaType.APPLICATION_JSON, HttpMethod.POST);
    }

    @Override
    public <T, S> HttpRequest<T, S> postUrlEncoded(HttpRequest<T, S> request, Class<S> responseType) {
        return this.exchange(request, responseType, MediaType.APPLICATION_FORM_URLENCODED, HttpMethod.POST);
    }

    @Override
    public <T, S> HttpRequest<T, S> get(HttpRequest<T, S> request, Class<S> responseType) {
        return this.exchange(request, responseType, null, HttpMethod.GET);
    }

    @Override
    public <T, S> HttpRequest<T, S> put(HttpRequest<T, S> request, Class<S> responseType) {
        return this.exchange(request, responseType, MediaType.APPLICATION_JSON, HttpMethod.PUT);
    }

    @Override
    public <T, S> HttpRequest<T, S> delete(HttpRequest<T, S> request, Class<S> responseType) {
        return this.exchange(request, responseType, null, HttpMethod.DELETE);
    }

    private <T, S> HttpRequest<T, S> exchange(HttpRequest<T, S> request, Class<S> responseType,
                                             MediaType contentType, HttpMethod method) {
        HttpEntity<?> httpEntity = generateHttpEntity(request, contentType);
        ResponseEntity<T> responseEntity = restTemplate.exchange(request.getUrl(), method, httpEntity,
                ParameterizedTypeReference.forType(responseType));
        return setResponse(request, responseEntity);
    }

    private <T, S> HttpEntity<?> generateHttpEntity(HttpRequest<T, S> request, MediaType contentType) {
        HttpHeaders httpHeaders = generateHttpHeaders(request);
        if (Objects.isNull(contentType)) {
            return new HttpEntity<>(httpHeaders);
        }
        if (contentType.equals(MediaType.APPLICATION_JSON)) {
            httpHeaders.setContentType(contentType);
            return new HttpEntity<>(request.getPayload(), httpHeaders);
        }
        if (contentType.equals(MediaType.APPLICATION_FORM_URLENCODED)) {
            httpHeaders.setContentType(contentType);
            @SuppressWarnings("unchecked") Map<T, T> map = (Map<T, T>) request.getPayload();
            MultiValueMap<T, T> formParams = new LinkedMultiValueMap<>();
            map.forEach(formParams::add);
            return new HttpEntity<>(formParams, httpHeaders);
        }
        throw new RuntimeException("Content type não suportado");
    }

    private <T, S> HttpRequest<T, S> setResponse(HttpRequest<T, S> request, ResponseEntity<T> responseEntity) {
        @SuppressWarnings("unchecked") S response = (S) responseEntity.getBody();
        request.setResponse(response);
        request.setStatusCodeResponse(responseEntity.getStatusCode().value());
        request.setLocation(Objects.requireNonNullElse(responseEntity.getHeaders().getLocation(),
                "no-location").toString());
        return request;
    }

    private <T, S> HttpHeaders generateHttpHeaders(HttpRequest<T, S> request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (Objects.nonNull(request.getUsername()) && Objects.nonNull(request.getPassword())){
            httpHeaders.setBasicAuth(request.getUsername(), request.getPassword());
        }
        if (Objects.nonNull(request.getBearerToken())) {
            httpHeaders.setBearerAuth(request.getBearerToken());
        }
        return httpHeaders;
    }
}