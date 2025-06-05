package org.example.technicaltaskexchange.controller;

import org.example.technicaltaskexchange.dto.request.ExchangeRequest;
import org.example.technicaltaskexchange.dto.response.ExchangeRateResponse;
import org.example.technicaltaskexchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exchange")

public class ExchangeContoller {

    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeContoller(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/live")
    public ResponseEntity<ExchangeRateResponse> getLiveRate(
            @RequestParam String source,
            @RequestParam String target) {
        ExchangeRateResponse response = exchangeService.getLiveRatesSafe(source, target);
        return new ResponseEntity<>(response, response != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/live")
    public ResponseEntity<ExchangeRateResponse> getLiveRate(@RequestBody ExchangeRequest request) {
        ExchangeRateResponse response = exchangeService.getLiveRatesSafe(request.getFrom(), request.getTo());
        return new ResponseEntity<>(response, response != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
