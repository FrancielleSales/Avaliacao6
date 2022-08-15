package br.com.compass.tests.validationsTests;

import br.com.compass.order.entities.OrderItemEntity;
import br.com.compass.order.entities.OrderItemOfferEntity;
import br.com.compass.order.validations.Validations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
@SpringBootTest
public class ValidationsTests {

    @Autowired
    private MockMvc mock;

    @Autowired
    private Validations validations;

    @Test
    void validateCpfTest(){
        // Validate incorrects cpf's
        String cpfFalse [] = {"", "O1234567899", "012345678 9", "0123456789", "012345678999",
                " ", "cpf", "01234567-89", "012345678.9", "abcdefghijk"};
        for(int i = 0; i < cpfFalse.length; i++){
            assertFalse(validations.validateCpf(cpfFalse[i]));
        }

        // Validate corrects cpf's
        String cpfTrue = "01234567899";
        assertTrue(validations.validateCpf(cpfTrue));
    }

    @Test
    void validateSecurityCodeTest(){
        // Validate incorrects secutity codes
        String securityCodeFalse [] = {"", "O1234567899", "1234", "1 2", "abc",
                " ", "_12", "1-2", "12.", "O12"};
        for(int i = 0; i < securityCodeFalse.length; i++){
            assertFalse(validations.validateSecutityCode(securityCodeFalse[i]));
        }

        // Validate corrects cpf's
        String securityCodeTrue = "123";
        assertTrue(validations.validateSecutityCode(securityCodeTrue));
    }

    @Test
    void validateDateItemsTest(){

        // Creating object to test
        OrderItemEntity item = new OrderItemEntity();
        OrderItemEntity item2 = new OrderItemEntity();
        List <OrderItemEntity> orderItemEntity = new ArrayList<>();
        orderItemEntity.add(item);
        orderItemEntity.add(item2);

        // Validating incorrect date and time
        orderItemEntity.get(0).setCreationDate(LocalDateTime.parse("2021-01-20T12:00:00"));
        orderItemEntity.get(0).setExpirationDate(LocalDateTime.parse("2021-01-20T12:10:00"));
        orderItemEntity.get(1).setCreationDate(LocalDateTime.parse("2021-01-21T12:00:01"));
        orderItemEntity.get(1).setExpirationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        assertFalse(validations.validateDateItems(orderItemEntity));

        orderItemEntity.get(0).setCreationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        orderItemEntity.get(0).setExpirationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        orderItemEntity.get(1).setCreationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        orderItemEntity.get(1).setExpirationDate(LocalDateTime.parse("2021-01-21T12:00:01"));
        assertFalse(validations.validateDateItems(orderItemEntity));

        // Validating correct date and time
        orderItemEntity.get(0).setCreationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        orderItemEntity.get(0).setExpirationDate(LocalDateTime.parse("2021-01-21T12:00:01"));
        orderItemEntity.get(1).setCreationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        orderItemEntity.get(1).setExpirationDate(LocalDateTime.parse("2021-04-16T12:00:00"));
        assertTrue(validations.validateDateItems(orderItemEntity));
    }

    @Test
    void validateDateOffersTest(){

        // Creating objects to test
        OrderItemOfferEntity itemOfferEntity = new OrderItemOfferEntity();
        OrderItemOfferEntity itemOfferEntity2 = new OrderItemOfferEntity();

        List <OrderItemEntity> orderItemEntity = new ArrayList<>();
        OrderItemEntity item = new OrderItemEntity();
        OrderItemEntity item2 = new OrderItemEntity();

        item.setOffer(itemOfferEntity);
        item2.setOffer(itemOfferEntity2);
        orderItemEntity.add(item);
        orderItemEntity.add(item2);

        // Validating incorrect date and time
        orderItemEntity.get(0).getOffer().setCreationDate(LocalDateTime.parse("2021-01-20T12:00:00"));
        orderItemEntity.get(0).getOffer().setExpirationDate(LocalDateTime.parse("2021-01-20T12:10:00"));
        orderItemEntity.get(1).getOffer().setCreationDate(LocalDateTime.parse("2021-01-21T12:00:01"));
        orderItemEntity.get(1).getOffer().setExpirationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        assertFalse(validations.validateDateOffers(orderItemEntity));

        orderItemEntity.get(0).getOffer().setCreationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        orderItemEntity.get(0).getOffer().setExpirationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        orderItemEntity.get(1).getOffer().setCreationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        orderItemEntity.get(1).getOffer().setExpirationDate(LocalDateTime.parse("2021-01-21T12:00:01"));
        assertFalse(validations.validateDateOffers(orderItemEntity));

        // Validating correct date and time
        orderItemEntity.get(0).getOffer().setCreationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        orderItemEntity.get(0).getOffer().setExpirationDate(LocalDateTime.parse("2021-01-21T12:00:01"));
        orderItemEntity.get(1).getOffer().setCreationDate(LocalDateTime.parse("2021-01-21T12:00:00"));
        orderItemEntity.get(1).getOffer().setExpirationDate(LocalDateTime.parse("2021-04-16T12:00:00"));
        assertTrue(validations.validateDateOffers(orderItemEntity));
    }

    @Test
    void validateExpirationOffersTest(){

        // Creating object to test
        OrderItemOfferEntity orderItemOfferEntity = new OrderItemOfferEntity();

        List <OrderItemEntity> orderItemEntities = new ArrayList<>();
        OrderItemEntity item = new OrderItemEntity();

        item.setOffer(orderItemOfferEntity);
        orderItemEntities.add(item);

        // Validating incorrect date and time
        orderItemEntities.get(0).getOffer().setExpirationDate(LocalDateTime.parse("2023-01-20T12:10:00"));
        assertFalse(validations.validateExpirationOffers(orderItemEntities));

        // Validating correct date and time
        orderItemEntities.get(0).getOffer().setExpirationDate(LocalDateTime.parse("2021-05-21T12:00:01"));
        assertTrue(validations.validateExpirationOffers(orderItemEntities));
    }
}
