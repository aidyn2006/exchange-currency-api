package org.example.technicaltaskexchange.pattern;

import org.apache.http.impl.client.CloseableHttpClient;


public interface HttpClientRegistry {
    CloseableHttpClient getClient();
}
