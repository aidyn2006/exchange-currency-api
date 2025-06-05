package org.example.technicaltaskexchange.dto.response;


import lombok.*;


public class ExchangeRateResponse {
    private String source;
    private String target;
    private Double rate;
    private String date;

    public ExchangeRateResponse(String source, String target, Double rate, String date) {
        this.source = source;
        this.target = target;
        this.rate = rate;
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}