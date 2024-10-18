package br.com.livoltek.core.api.common.service;

import br.com.livoltek.core.internal.common.dto.HttpRequest;


public interface HttpService {

    <T, S> HttpRequest<T,S> post(HttpRequest<T,S> request, Class<S> responseType);

    <T, S> HttpRequest<T, S> postUrlEncoded(HttpRequest<T, S> request, Class<S> responseType);

    <T, S> HttpRequest<T,S> get(HttpRequest<T,S> request, Class<S> responseType);

    <T, S> HttpRequest<T,S> put(HttpRequest<T,S> request, Class<S> responseType);

    <T, S> HttpRequest<T, S> delete(HttpRequest<T, S> request, Class<S> responseType);

}