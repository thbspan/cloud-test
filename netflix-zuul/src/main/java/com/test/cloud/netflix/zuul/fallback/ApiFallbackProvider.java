package com.test.cloud.netflix.zuul.fallback;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

//@Component
public class ApiFallbackProvider implements FallbackProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFallbackProvider.class);
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {

            public HttpStatus getStatusCode() {
                return HttpStatus.OK;
            }

            public int getRawStatusCode() {
                return HttpStatus.OK.value();
            }

            public String getStatusText() {
                return HttpStatus.OK.getReasonPhrase();
            }

            public void close() {
            }

            public InputStream getBody() {
                LOGGER.warn("call exception", cause.getCause());
                // 响应内容
                String bodyText = String.format("{\"code\": 500,\"message\": \"Service unavailable:%s\"}", cause.getLocalizedMessage());
                return new ByteArrayInputStream(bodyText.getBytes());
            }

            public HttpHeaders getHeaders() {
                // 响应头
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
