package org.example.technicaltaskexchange.controller;

import lombok.RequiredArgsConstructor;
import org.example.technicaltaskexchange.config.ExchangeConfig;
import org.example.technicaltaskexchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exchange")

public class ExchangeContoller {



    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeContoller(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }


    @GetMapping("/live")
    public String getLiveExchangeRate() {
        return exchangeService.getLiveRates();
    }

}
