package org.example.technicaltaskexchange.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@NoArgsConstructor

public class ExchangeConfig {

    @Value("${ACCESS_KEY}")
    private String ACCESS_KEY = null;

    @Value("${BASE_URL}")
    private String BASE_URL = null;

    @Value("${ENDPOINT}")
    private String ENDPOINT = null;

    CloseableHttpClient httpClient = HttpClients.createDefault();

    public String getAccessKey() {
        return ACCESS_KEY;
    }

    public void setAccessKey(String ACCESS_KEY) {
        this.ACCESS_KEY = ACCESS_KEY;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }

    public void setBaseUrl(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    public String getEndpoint() {
        return ENDPOINT;
    }

    public void setEndpoint(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }
}
