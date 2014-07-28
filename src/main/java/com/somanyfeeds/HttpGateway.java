package com.somanyfeeds;

import com.google.api.client.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.somanyfeeds.Utils.readInputStream;

@Component
public class HttpGateway {
    private final HttpRequestFactory requestFactory;

    @Autowired
    public HttpGateway(HttpTransport transport) {
        this.requestFactory = transport.createRequestFactory();
    }

    public String get(String url) throws IOException {
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
        HttpResponse response = request.execute();

        try {
            return readInputStream(response.getContent());
        } finally {
            response.disconnect();
        }
    }
}
