package br.com.compass.order.controllers;

import br.com.compass.order.advices.GenericException;
import br.com.compass.order.dtos.OrderItemDto;
import br.com.compass.order.services.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private OrderItemService service;

    @PatchMapping("/{id}")
    public @ResponseBody void updateItemById(@PathVariable Long id, @RequestBody OrderItemDto item)
            throws GenericException {
        log.info("updateItemById() - Calling the service");
        service.updateItemById(id, item);
    }
}
