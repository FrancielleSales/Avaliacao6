package br.com.compass.site.controllers;

import br.com.compass.site.advices.GenericException;
import br.com.compass.site.dtos.checkout.CheckoutDto;
import br.com.compass.site.dtos.orderResponse.OrderResponseDto;
import br.com.compass.site.services.CheckoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;


    @PostMapping
    public ResponseEntity<OrderResponseDto> createCheckout(@Valid @RequestBody CheckoutDto checkoutDto) throws GenericException {
        log.info("createCheckout() - START - Calling the service");
        OrderResponseDto orderReponse = checkoutService.addNewCheckout(checkoutDto);
        return new ResponseEntity<>(orderReponse, HttpStatus.CREATED);
    }

}
