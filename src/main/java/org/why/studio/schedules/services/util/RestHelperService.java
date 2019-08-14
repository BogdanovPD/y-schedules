package org.why.studio.schedules.services.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

public interface RestHelperService {
    <T> T sendGetRequest(UriComponentsBuilder urlBuilder, T returnObj) throws RestClientException;

    <T> T sendGetRequestWithHeaders(HttpHeaders httpHeaders, UriComponentsBuilder urlBuilder, T returnObj) throws RestClientException;

    <T> ResponseEntity<T> sendGetRequestAndGetFullResponse(UriComponentsBuilder urlBuilder, T returnObj) throws RestClientException;

    <T> T sendGetRequestWithBasicAuth(UriComponentsBuilder urlBuilder, T returnObj, String basicAuthUser, String basicAuthPass) throws RestClientException;

    <T> T sendGetRequestWithBearerAuth(UriComponentsBuilder urlBuilder, T returnObj, String accessToken) throws RestClientException;

    <T, V> T sendPostRequest(String url, V data, MediaType type, T returnObj) throws RestClientException;

    <T, V> T sendPostRequestWithBasicAuth(String url, V data, MediaType type, T returnObj, String basicAuthUser, String basicAuthPass) throws RestClientException;

    <T, V> T sendPostRequestWithBearerAuth(String url, V data, MediaType type, T returnObj, String accessToken) throws RestClientException;

    <T> T sendDeleteRequest(String url, T returnObj) throws RestClientException;

    <T> T sendDeleteRequestWithBasicAuth(String url, T returnObj, String basicAuthUser, String basicAuthPass) throws RestClientException;

    <T> T sendDeleteRequestWithBearerAuth(String url, T returnObj, String accessToken) throws RestClientException;

    <T, V> T sendPatchRequest(String url, V data, MediaType type, T returnObj) throws RestClientException;

    <T, V> T sendPatchRequestWithBasicAuth(String url, V data, MediaType type, T returnObj, String basicAuthUser, String basicAuthPass) throws RestClientException;

    <T, V> T sendPatchRequestWithBearerAuth(String url, V data, MediaType type, T returnObj, String accessToken) throws RestClientException;
}