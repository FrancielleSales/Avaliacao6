package br.com.compass.site.controllers;

import br.com.compass.site.advices.GenericException;
import br.com.compass.site.dtos.ItemsDto;
import br.com.compass.site.services.ItemsService;
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
@RequestMapping("/api/item/")
public class ItemsController {

    @Autowired
    private ItemsService service;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<ItemsDto> createItem(@Valid @RequestBody ItemsDto item) {
        log.info("createItem() - START - Calling the service");
        ItemsDto itemsDto = service.addNewItem(item);
        return new ResponseEntity<>(itemsDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ItemsDto>> findItems(Long id, Pageable pageable) throws GenericException{
        log.info("findItems() - START - Calling the service");
        return ResponseEntity.ok(service.findAllItems(id, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemsDto> getItem(@PathVariable long id) throws GenericException {
        log.info("getItem() - START - Calling the service");
        return ResponseEntity.ok(service.findItemById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Long id) throws GenericException {
        log.info("deleteItem() - START - Calling the service");
        return service.deleteItemById(id);
    }

    @PutMapping("/{id}")
    public  @ResponseBody ResponseEntity<Object> updateItemById(@PathVariable Long id, @RequestBody ItemsDto item)
            throws GenericException {
        log.info("updateItemById() - START - Calling the service");
        return service.updateItemById(id, item);
    }
}
