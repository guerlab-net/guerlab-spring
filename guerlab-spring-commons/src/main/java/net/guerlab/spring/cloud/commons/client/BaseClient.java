package net.guerlab.spring.cloud.commons.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.web.result.Result;

public abstract class BaseClient {

    protected final HttpHeaders headers = new HttpHeaders();

    protected final RestTemplate restTemplate;

    private final String baseUrl;

    public BaseClient(String baseUrl, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;

        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    protected static <T> T parseResponse(
            ResponseEntity<Result<T>> responseEntity) {
        Result<T> result = responseEntity.getBody();

        if (result == null) {
            throw new ApplicationException("No response content", 500);
        } else if (result.isStatus()) {
            return result.getData();
        } else {
            throw new ApplicationException(result.getMessage(), result.getErrorCode());
        }
    }

    /**
     * 返回 baseUrl
     *
     * @return baseUrl
     */
    protected final String getBaseUrl() {
        return baseUrl;
    }
}
