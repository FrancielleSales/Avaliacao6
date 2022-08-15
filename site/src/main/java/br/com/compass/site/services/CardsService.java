package br.com.compass.site.services;

import br.com.compass.site.advices.GenericException;
import br.com.compass.site.dtos.CardsDto;
import br.com.compass.site.entities.CardsEntity;
import br.com.compass.site.entities.ClientsEntity;
import br.com.compass.site.repositories.CardsRepository;
import br.com.compass.site.repositories.ClientsRepository;
import br.com.compass.site.utils.MapUtil;
import br.com.compass.site.validations.Validations;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CardsService {

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MapUtil mapUtil;

    // Post card
    public CardsDto addNewCard(String cpf, CardsDto cardsDto) throws GenericException {
        ClientsEntity clientsEntity = clientsRepository.findById(cpf).orElseThrow(
                () -> new GenericException("No client found with cpf: " + cpf));

        if (cardsRepository.existsByCardNumber(cardsDto.getCardNumber())){
            throw new GenericException("This numeroCartao is already registered!");
        }

        if (!Validations.validateSecurityCode(cardsDto.getSecurityCode())) {
            throw new GenericException("The codigoSeguranca field must be 3 numeric characters");
        }

        log.info("addNewCard() - START - Saving card with number '{}'", cardsDto.getCardNumber());

        CardsEntity cardsEntity = modelMapper.map(cardsDto, CardsEntity.class);

        clientsEntity.getCards().add(cardsEntity);

        clientsRepository.save(clientsEntity);

        log.info("addNewCard() - END - Card successfully saved!");

        return modelMapper.map(cardsEntity, CardsDto.class);
    }

    // Get cards by cpf
    public List<CardsDto> findCardsByCpf(String cpf) throws GenericException {
        ClientsEntity clientsEntity = clientsRepository.findById(cpf).orElseThrow(
                () -> new GenericException("No client found with cpf: " + cpf));

        List<CardsEntity> cards = clientsEntity.getCards();
        List<CardsDto> cardsDto = new ArrayList<>();

        for (CardsEntity c : cards) {
            CardsDto temp = modelMapper.map(c, CardsDto.class);
            cardsDto.add(temp);
        }
        return cardsDto;
    }

    // Update card by cpf
    public ResponseEntity<Object> updateCardByCpf(String cpf, CardsDto cardsDto) throws GenericException {
        ClientsEntity clientsEntity = clientsRepository.findById(cpf).orElseThrow(
                () -> new GenericException("No client found with cpf: " + cpf));

        log.info("updateCardByCpf() - START - Updating card with cpf: {}", cpf);

        try{
            for (int i = 0; i < clientsEntity.getCards().size(); i++) {
                if (clientsEntity.getCards().get(i).getCardNumber().equals(cardsDto.getCardNumber())) {
                    clientsEntity.getCards().get(i).setExpirationMonth(cardsDto.getExpirationMonth() != null ?
                            cardsDto.getExpirationMonth() : clientsEntity.getCards().get(i).getExpirationMonth());
                    clientsEntity.getCards().get(i).setExpirationYear(cardsDto.getExpirationYear() != null ?
                            cardsDto.getExpirationYear() : clientsEntity.getCards().get(i).getExpirationYear());
                    clientsEntity.getCards().get(i).setSecurityCode(cardsDto.getSecurityCode() != null ?
                            cardsDto.getSecurityCode() : clientsEntity.getCards().get(i).getSecurityCode());
                }
            }
            clientsRepository.save(clientsEntity);

            log.info("updateCardByCpf() - END - Card successfully updated");

            return new ResponseEntity<>("{\"message\": \"Card successfully updated!\"}", HttpStatus.OK);
        } catch (Exception ex) {
            throw new GenericException(ex.getMessage());
        }
    }

}
