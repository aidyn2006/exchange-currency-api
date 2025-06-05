package org.example.technicaltaskexchange.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.technicaltaskexchange.dto.request.ExchangeRequest;
import org.example.technicaltaskexchange.dto.response.ExchangeRateResponse;
import org.example.technicaltaskexchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Валютный обмен", description = "API для получения курсов валют")
@RequestMapping("/api/v1/exchange")

public class ExchangeContoller {

    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeContoller(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/live")
    @Operation(
            summary = "Получить актуальный курс валют по параметрам запроса (GET)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Курс валют успешно получен",
                            content = @Content(schema = @Schema(implementation = ExchangeRateResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public ResponseEntity<ExchangeRateResponse> getLiveRate(
            @RequestParam String source,
            @RequestParam String target) {
        ExchangeRateResponse response = exchangeService.getLiveRatesSafe(source, target);
        return new ResponseEntity<>(response, response != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/live")
    @Operation(
            summary = "Получить актуальный курс валют через POST запрос с JSON телом",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Курс валют успешно получен",
                            content = @Content(schema = @Schema(implementation = ExchangeRateResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public ResponseEntity<ExchangeRateResponse> getLiveRate(@RequestBody ExchangeRequest request) {
        ExchangeRateResponse response = exchangeService.getLiveRatesSafe(request.getFrom(), request.getTo());
        return new ResponseEntity<>(response, response != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
