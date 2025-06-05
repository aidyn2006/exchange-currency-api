package org.example.technicaltaskexchange.service;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.example.technicaltaskexchange.config.ExchangeConfig;
import org.example.technicaltaskexchange.dto.response.ExchangeRateResponse;
import org.example.technicaltaskexchange.handling.ExternalApiException;
import org.example.technicaltaskexchange.pattern.HttpClientRegistry;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ExchangeService {

    private final ExchangeConfig config;
    private final HttpClientRegistry clientRegistry;
    private static final Logger log = LoggerFactory.getLogger(ExchangeService.class);


    @Autowired
    public ExchangeService(ExchangeConfig config, HttpClientRegistry clientRegistry) {
        this.config = config;
        this.clientRegistry = clientRegistry;
    }

    @Cacheable(value = "exchangeRates", key = "#sourceCurrency + '_' + #targetCurrency")
    public ExchangeRateResponse getLiveRates(String sourceCurrency, String targetCurrency) {
        log.info("Request received to fetch exchange rate: {} -> {}", sourceCurrency, targetCurrency);

        try {
            CloseableHttpClient httpClient = clientRegistry.getClient();

            String url = config.getBaseUrl() + config.getEndpoint() +
                    "?access_key=" + config.getAccessKey() +
                    "&source=" + sourceCurrency +
                    "&currencies=" + targetCurrency;

            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

            Date timeStampDate = new Date(exchangeRates.getLong("timestamp") * 1000);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
            String formattedDate = dateFormat.format(timeStampDate);

            String key = sourceCurrency + targetCurrency;
            double rate = exchangeRates.getJSONObject("quotes").getDouble(key);
            String source = exchangeRates.getString("source");

            response.close();

            log.info("Successfully fetched rate {} -> {}: {} (Date: {})", sourceCurrency, targetCurrency, rate, formattedDate);
            return new ExchangeRateResponse(source, targetCurrency, rate, formattedDate);

        } catch (Exception e) {
            log.error("Failed to fetch exchange rates for {}->{}", sourceCurrency, targetCurrency, e);
            throw new ExternalApiException("Failed to fetch exchange rates", e);
        }
    }


    public ExchangeRateResponse getLiveRatesSafe(String sourceCurrency, String targetCurrency) {
        try {
            return getLiveRates(sourceCurrency, targetCurrency);
        } catch (ExternalApiException e) {
            log.warn("Handled external API error: {}", e.getMessage());
            return null;
        }
    }
}
