package com.somanyfeeds;

import com.google.api.client.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.somanyfeeds.Utils.readInputStream;

@Component
public class HttpGateway {
    private final Logger logger = LoggerFactory.getLogger(HttpGateway.class);
    private final HttpRequestFactory requestFactory;

    @Autowired
    public HttpGateway(HttpTransport transport) {
        this.requestFactory = transport.createRequestFactory();
    }

    public String get(String url) throws IOException {
        logger.debug("Performing GET - {}", url);
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
        HttpResponse response = request.execute();

        try {
            String body = readInputStream(response.getContent());

            if (response.getStatusCode() >= 400) {
                logger.error("Received an error with status {}", response.getStatusCode());
                logger.error("Body **********\n{}\n**********", body);
            } else {
                logger.debug("Received status {}", response.getStatusCode());
            }

            return body;
        } finally {
            response.disconnect();
        }
    }
}
