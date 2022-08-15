package br.com.compass.tests.serviceTests;

import br.com.compass.order.advices.GenericException;
import br.com.compass.order.dtos.OrderItemDto;
import br.com.compass.order.entities.OrderItemEntity;
import br.com.compass.order.repositories.OrderItemRepository;
import br.com.compass.order.services.OrderItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class OrderItemServiceTests {

    @InjectMocks
    private OrderItemService orderItemService;
    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderItemDto orderItemDto;
    @Mock
    private OrderItemEntity orderItemEntity;

    private static final Long ID = 1L;
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final LocalDateTime CREATIONDATE = LocalDateTime.parse("2021-01-21T12:00:00");
    private static final LocalDateTime EXPIRATIONDATE = LocalDateTime.parse("2023-01-21T12:00:00");
    private static final BigDecimal VALUE = BigDecimal.valueOf(12.50);

    @BeforeEach
    void SetUp(){
        MockitoAnnotations.openMocks(this);
        inicializeOrderItem();
    }

    @Test
    void updateItemByIdTest() throws GenericException {

        Mockito.when(orderItemRepository.findById(ID)).thenReturn(Optional.of(orderItemEntity));
        Mockito.when(orderItemRepository.save(orderItemEntity)).thenReturn(orderItemEntity);

        String newName = "new";
        orderItemDto.setName(newName);

        orderItemService.updateItemById(ID, orderItemDto);

        Assertions.assertEquals(newName, orderItemEntity.getName());
    }

    private void inicializeOrderItem(){

        orderItemDto = new OrderItemDto();
        orderItemDto.setName(NAME);
        orderItemEntity = new OrderItemEntity(ID, NAME, CREATIONDATE, EXPIRATIONDATE, VALUE, DESCRIPTION, null);

    }

}
