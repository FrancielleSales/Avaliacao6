package br.com.compass.order.controllers;

import br.com.compass.order.advices.GenericException;
import br.com.compass.order.dtos.OrderDto;
import br.com.compass.order.producers.PaymentProducer;
import br.com.compass.order.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/pedido/")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentProducer paymentProducer;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto order) throws GenericException{
        log.info("createOrder() - START - Calling the service");
        OrderDto paymentDto = service.addNewOrder(order);

        paymentProducer.sendPayment(paymentDto);

        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<OrderDto>> findAll(String cpf, Pageable pageable) throws GenericException{
        log.info("findAll() - START - Calling the service");
        return ResponseEntity.ok(service.getAllOrders(cpf, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable long id) throws GenericException {
        log.info("getOrder() - START - Calling the service");
        return ResponseEntity.ok(service.getOrder(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePedido(@PathVariable Long id) throws GenericException {
        log.info("deletePedido() - START - Calling the service");
        return service.deleteOrderById(id);
    }

    @PatchMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> updateOrderById(@PathVariable Long id, @RequestBody OrderDto order)
            throws GenericException {
        log.info("updateOrderById() - START - Calling the service");
        return service.updateOrderById(id, order);
    }
}
