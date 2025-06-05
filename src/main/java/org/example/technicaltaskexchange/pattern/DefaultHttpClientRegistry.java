package org.example.technicaltaskexchange.pattern;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Component
public class DefaultHttpClientRegistry implements HttpClientRegistry {

    private final CloseableHttpClient client = HttpClients.createDefault();

    @Override
    public CloseableHttpClient getClient() {
        return HttpClients.createDefault();
    }
}
