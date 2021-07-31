package com.dmartyni.postconsumer.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class RestTemplateResponseErrorHandler  implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return new DefaultResponseErrorHandler().hasError(httpResponse);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            log.error("Server error: " + httpResponse.getStatusCode());
        } else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            log.error("Client error: " + httpResponse.getStatusCode() + " " + httpResponse.getBody());
        }
    }
}
