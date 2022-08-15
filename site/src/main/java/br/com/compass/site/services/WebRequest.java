package br.com.compass.site.services;

import br.com.compass.site.dtos.orderRequest.OrderDto;
import br.com.compass.site.dtos.orderResponse.OrderResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class WebRequest {

    private final WebClient webClient;


    public WebRequest() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public OrderResponseDto sendOrder(OrderDto orderDto) {
        OrderResponseDto response = null;

        try {
            response = webClient
                    .method(HttpMethod.POST)
                    .uri("/pedido/")
                    .body(Mono.just(orderDto), OrderDto.class)
                    .retrieve()
                    .bodyToMono(OrderResponseDto.class)
                    .block();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage() + " " + e.getMessage());
        }

        return response;
    }

}