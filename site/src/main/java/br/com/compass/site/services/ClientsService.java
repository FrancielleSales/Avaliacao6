package br.com.compass.site.services;

import br.com.compass.site.advices.GenericException;
import br.com.compass.site.dtos.ClientsDto;
import br.com.compass.site.entities.ClientsEntity;
import br.com.compass.site.repositories.ClientsRepository;
import br.com.compass.site.utils.MapUtil;
import br.com.compass.site.validations.Validations;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Service
public class ClientsService {

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MapUtil mapUtil;

    // Post client
    public ClientsDto addNewClient(ClientsDto clientsDto) throws GenericException {
        log.info("addNewClient() - START - Saving client with cpf '{}'", clientsDto.getCpf());

        if (!Validations.validateCpf(clientsDto.getCpf())){
            throw new GenericException("The cpf field must be 11 numeric characters");
        }

        if (clientsRepository.existsById(clientsDto.getCpf())){
            throw new GenericException("This cpf is already registered!");
        }

        ClientsEntity clientsSave = modelMapper.map(clientsDto, ClientsEntity.class);
        clientsSave = clientsRepository.save(clientsSave);
        log.info("addNewClient() - END - Client saved with cpf '{}'", clientsSave.getCpf());
        return modelMapper.map(clientsSave, ClientsDto.class);
    }

    // Get all clients
    public Page<ClientsDto> findAllClients(@RequestParam(required = false) String cpf,
                                           @PageableDefault(size = 100, sort = "cpf", direction = Sort.Direction.ASC)
                                       Pageable pageable) throws GenericException {
        Page<ClientsEntity> page;

        try {
            if (cpf == null) {
                page = clientsRepository.findAll(pageable);
            } else {
                page = clientsRepository.findByCpf(cpf, pageable);
            }
        } catch (Exception ex) {
            throw new GenericException(ex.getMessage());
        }

        return mapUtil.mapEntityPageIntoDtoPage(page, ClientsDto.class);
    }

    // Get client by cpf
    public ClientsDto findClientsByCpf(String cpf) throws GenericException {
        ClientsEntity clientsEntity = clientsRepository.findById(cpf).orElseThrow(
                () -> new GenericException("No client found with cpf: " + cpf));

        return modelMapper.map(clientsEntity, ClientsDto.class);
    }

    // Delete client by cpf
    public ResponseEntity<Object> deleteClientByCpf(String cpf) throws GenericException {
        log.info("deleteClientByCpf() - START - Deleting client with cpf: {}", cpf);
        ClientsEntity clientsEntity = clientsRepository.findById(cpf).orElseThrow(
                () -> new GenericException("No client found with cpf: " + cpf));
        clientsRepository.delete(clientsEntity);
        log.info("deleteClientByCpf() - END - Client successfully deleted");
        return new ResponseEntity<>("{\"message\": \"Client successfully deleted!\"}", HttpStatus.OK);
    }

    // Update client by cpf
    public ResponseEntity<Object> updateClientByCpf(String cpf, ClientsDto clientsDto) throws GenericException {
        log.info("updateClientByCpf() - START - Updating client with cpf: {}", cpf);
        ClientsEntity clientsEntity = clientsRepository.findById(cpf).orElseThrow(
                () -> new GenericException("No client found with cpf: " + cpf));

        if (!Validations.validateCpf(clientsDto.getCpf())) {
            throw new GenericException("The cpf field must be 11 numeric characters");
        }

            try {
            clientsEntity.setName(clientsDto.getName() != null ? clientsDto.getName() : clientsEntity.getName());
            clientsEntity.setCpf(clientsDto.getCpf() != null ? clientsDto.getCpf() :
                    clientsEntity.getCpf());
            clientsEntity.setName(clientsDto.getName() != null ? clientsDto.getName() :
                    clientsEntity.getName());
            clientsRepository.save(clientsEntity);
            log.info("updateClientByCpf() - END - Client successfully updated");
            return new ResponseEntity<>("{\"message\": \"Client successfully updated!\"}", HttpStatus.OK);
        } catch (Exception ex) {
            throw new GenericException(ex.getMessage());
        }
    }
}
