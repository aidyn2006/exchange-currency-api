package org.example.technicaltaskexchange.service;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.technicaltaskexchange.config.ExchangeConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service

public class ExchangeService {

    private final ExchangeConfig config;

    @Autowired
    public ExchangeService(ExchangeConfig config) {
        this.config = config;
    }

    public String getLiveRates() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = config.getBaseUrl() + config.getEndpoint() + "?access_key=" + config.getAccessKey();
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

            Date timeStampDate = new Date(exchangeRates.getLong("timestamp") * 1000);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
            String formattedDate = dateFormat.format(timeStampDate);

            double usdToGbp = exchangeRates.getJSONObject("quotes").getDouble("USDGBP");
            String source = exchangeRates.getString("source");

            return "1 " + source + " in GBP: " + usdToGbp + " (Date: " + formattedDate + ")";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
