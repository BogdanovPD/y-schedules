package org.why.studio.schedules.services.util.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.why.studio.schedules.services.util.RestHelperService;

@Service
@RequiredArgsConstructor
public class RestHelperServiceImpl implements RestHelperService {
    private final RestTemplate restTemplate;

    public <T> T sendGetRequest(UriComponentsBuilder urlBuilder, T returnObj) throws RestClientException {
        return this.makeGetRequest((HttpHeaders)null, urlBuilder, returnObj).getBody();
    }

    public <T> T sendGetRequestWithHeaders(HttpHeaders httpHeaders, UriComponentsBuilder urlBuilder, T returnObj) throws RestClientException {
        return this.makeGetRequest(httpHeaders, urlBuilder, returnObj).getBody();
    }

    public <T> ResponseEntity<T> sendGetRequestAndGetFullResponse(UriComponentsBuilder urlBuilder, T returnObj) throws RestClientException {
        return this.makeGetRequest((HttpHeaders)null, urlBuilder, returnObj);
    }

    public <T> T sendGetRequestWithBasicAuth(UriComponentsBuilder urlBuilder, T returnObj, String basicAuthUser, String basicAuthPass) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUser, basicAuthPass);
        return this.makeGetRequest(httpHeaders, urlBuilder, returnObj).getBody();
    }

    public <T> T sendGetRequestWithBearerAuth(UriComponentsBuilder urlBuilder, T returnObj, String accessToken) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        return this.makeGetRequest(httpHeaders, urlBuilder, returnObj).getBody();
    }

    public <T, V> T sendPostRequest(String url, V data, MediaType type, T returnObj) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(type);
        return this.makePostRequest(httpHeaders, url, data, type, returnObj);
    }

    public <T, V> T sendPostRequestWithBasicAuth(String url, V data, MediaType type, T returnObj, String basicAuthUser, String basicAuthPass) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUser, basicAuthPass);
        return this.makePostRequest(httpHeaders, url, data, type, returnObj);
    }

    public <T, V> T sendPostRequestWithBearerAuth(String url, V data, MediaType type, T returnObj, String accessToken) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        return this.makePostRequest(httpHeaders, url, data, type, returnObj);
    }

    public <T> T sendDeleteRequest(String url, T returnObj) throws RestClientException {
        return this.makeDeleteRequest((HttpHeaders)null, url, returnObj);
    }

    public <T> T sendDeleteRequestWithBasicAuth(String url, T returnObj, String basicAuthUser, String basicAuthPass) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUser, basicAuthPass);
        return this.makeDeleteRequest(httpHeaders, url, returnObj);
    }

    public <T> T sendDeleteRequestWithBearerAuth(String url, T returnObj, String accessToken) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        return this.makeDeleteRequest(httpHeaders, url, returnObj);
    }

    public <T, V> T sendPatchRequest(String url, V data, MediaType type, T returnObj) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(type);
        return this.makePatchRequest(httpHeaders, url, data, type, returnObj);
    }

    public <T, V> T sendPatchRequestWithBasicAuth(String url, V data, MediaType type, T returnObj, String basicAuthUser, String basicAuthPass) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUser, basicAuthPass);
        return this.makePatchRequest(httpHeaders, url, data, type, returnObj);
    }

    public <T, V> T sendPatchRequestWithBearerAuth(String url, V data, MediaType type, T returnObj, String accessToken) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        return this.makePatchRequest(httpHeaders, url, data, type, returnObj);
    }

    private <T> ResponseEntity<T> makeGetRequest(HttpHeaders httpHeaders, UriComponentsBuilder urlBuilder, T returnObj) throws RestClientException {
        HttpEntity<?> request = new HttpEntity(httpHeaders);
        return this.restTemplate.exchange(urlBuilder.toUriString(), HttpMethod.GET, request, this.getParameterizedType(returnObj), new Object[0]);
    }

    private <T, V> T makePostRequest(HttpHeaders httpHeaders, String url, V data, MediaType type, T returnObj) throws RestClientException {
        httpHeaders.setContentType(type);
        HttpEntity<V> request = new HttpEntity(data, httpHeaders);
        ResponseEntity<T> response = this.restTemplate.exchange(url, HttpMethod.POST, request, this.getParameterizedType(returnObj), new Object[0]);
        return response.getBody();
    }

    private <T, V> T makePatchRequest(HttpHeaders httpHeaders, String url, V data, MediaType type, T returnObj) throws RestClientException {
        httpHeaders.setContentType(type);
        HttpEntity<V> request = new HttpEntity(data, httpHeaders);
        ResponseEntity<T> responseEntity = this.restTemplate.exchange(url, HttpMethod.PATCH, request, this.getParameterizedType(returnObj), new Object[0]);
        return responseEntity.getBody();
    }

    private <T, V> T makeDeleteRequest(HttpHeaders httpHeaders, String url, T returnObj) throws RestClientException {
        HttpEntity<V> request = new HttpEntity(httpHeaders);
        ResponseEntity<T> responseEntity = this.restTemplate.exchange(url, HttpMethod.DELETE, request, this.getParameterizedType(returnObj), new Object[0]);
        return responseEntity.getBody();
    }

    private <T> ParameterizedTypeReference<T> getParameterizedType(T obj) {
        return ParameterizedTypeReference.forType(obj.getClass());
    }
}