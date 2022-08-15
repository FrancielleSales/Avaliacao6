package br.com.compass.site.controllers;

import br.com.compass.site.advices.GenericException;
import br.com.compass.site.dtos.CardsDto;
import br.com.compass.site.dtos.ClientsDto;
import br.com.compass.site.services.CardsService;
import br.com.compass.site.services.ClientsService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cliente")
public class ClientsController {

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private CardsService cardsService;

    @Autowired
    private ModelMapper modelMapper;

    // Clients

    @PostMapping
    public ResponseEntity<ClientsDto> createClient(@Valid @RequestBody ClientsDto clients) throws GenericException {
        log.info("createClient() - START - Calling the service");
        ClientsDto clientsDto = clientsService.addNewClient(clients);
        return new ResponseEntity<>(clientsDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ClientsDto>> findAllClients(String cpf, Pageable pageable) throws GenericException {
        log.info("findAllClients() - START - Calling the service");
        return ResponseEntity.ok(clientsService.findAllClients(cpf, pageable));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClientsDto> findClient(@PathVariable String cpf) throws GenericException {
        log.info("findClient() - START - Calling the service");
        return ResponseEntity.ok(clientsService.findClientsByCpf(cpf));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Object> deleteClient(@PathVariable String cpf) throws GenericException {
        log.info("deleteClient() - START - Calling the service");
        return clientsService.deleteClientByCpf(cpf);
    }

    @PutMapping("/{cpf}")
    public  @ResponseBody ResponseEntity<Object> updateClient(@PathVariable String cpf, @RequestBody ClientsDto clients)
            throws GenericException {
        log.info("updateClient() - START - Calling the service");
        return clientsService.updateClientByCpf(cpf, clients);
    }

    //------------------------------------------------------------------------------------------------------------------

    // Clients cards


    @PostMapping("/{cpf}/cartoes")
    public ResponseEntity<CardsDto> createCard(@PathVariable String cpf, @Valid @RequestBody CardsDto cards)
            throws GenericException {
        log.info("createCard() - START - Calling the service");
        CardsDto cardsDto = cardsService.addNewCard(cpf, cards);
        return new ResponseEntity<>(cardsDto, HttpStatus.CREATED);
    }

      @GetMapping("/{cpf}/cartoes")
      public ResponseEntity<List<CardsDto>> findCard(@PathVariable String cpf) throws GenericException {
        log.info("findCard() - START - Calling the service");
        return ResponseEntity.ok(cardsService.findCardsByCpf(cpf));
}

    @PutMapping("/{cpf}/cartoes")
      public  @ResponseBody ResponseEntity<Object> updateCard(@PathVariable String cpf, @RequestBody CardsDto cards)
              throws GenericException {
        log.info("updateCard() - START - Calling the service");
        return cardsService.updateCardByCpf(cpf, cards);
    }
}
