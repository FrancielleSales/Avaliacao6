package br.com.compass.payments.clients;

import br.com.compass.payments.authParameters.PbBankReceiveAuthParameters;
import br.com.compass.payments.authParameters.PbBankRequestAuthParameters;
import br.com.compass.payments.dtos.paymentRequestDtos.PaymentRequestDto;
import br.com.compass.payments.dtos.paymentResponseDtos.PaymentResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PbBankClient {

    private final WebClient webClient;

    private final PbBankRequestAuthParameters requestAuthParameters;

    private PbBankReceiveAuthParameters receiveAuthParameters;

    public PbBankClient() {
        webClient = WebClient.builder()
                .baseUrl("https://pb-getway-payment.herokuapp.com/v1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        requestAuthParameters = new PbBankRequestAuthParameters();

        receiveAuthParameters = null;
    }

    public Boolean auth() {
        log.info("auth() - START - Authenticating the user");
        receiveAuthParameters = webClient
                .method(HttpMethod.POST)
                .uri("/auth")
                .body(Mono.just(requestAuthParameters), PbBankRequestAuthParameters.class)
                .retrieve()
                .bodyToMono(PbBankReceiveAuthParameters.class)
                .block();
        log.info("auth() - END - Authenticated user");

        return true;
    }

    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequestDto) {
        log.info("Processing payment");
        PaymentResponseDto response = null;

        try {
            response = webClient
                    .method(HttpMethod.POST)
                    .uri("/payments/credit-card")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + receiveAuthParameters.getAccessToken())
                    .body(Mono.just(paymentRequestDto), PaymentRequestDto.class)
                    .retrieve()
                    //.onStatus(HttpStatus::is4xxClientError,
                            //error -> Mono.error(new RuntimeException(error.toString())))
                    .bodyToMono(PaymentResponseDto.class)
                    .log()
                    .block();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage() + " " + e.getMessage());
        }

        return response;
    }
}