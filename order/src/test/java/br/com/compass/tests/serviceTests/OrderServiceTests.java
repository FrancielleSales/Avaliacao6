package br.com.compass.tests.serviceTests;

import br.com.compass.order.enums.*;
import br.com.compass.order.advices.GenericException;
import br.com.compass.order.dtos.OrderDto;
import br.com.compass.order.dtos.OrderItemDto;
import br.com.compass.order.dtos.OrderPaymentDto;
import br.com.compass.order.entities.OrderEntity;
import br.com.compass.order.entities.OrderItemEntity;
import br.com.compass.order.entities.OrderPaymentEntity;
import br.com.compass.order.repositories.OrderRepository;
import br.com.compass.order.services.OrderService;
import br.com.compass.order.utils.MapUtils;
import br.com.compass.order.validations.Validations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

;

@SpringBootTest
public class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private MapUtils mapUtils;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Validations validations;

    @Mock
    private ModelMapper modelMapper;

    private OrderItemDto orderItemDto;
    private OrderItemEntity orderItemEntity;
    private List<OrderItemDto> listDto;
    private List<OrderItemEntity> listEntity;
    private OrderDto orderDto;
    private OrderEntity orderEntity;
    private static final Long ID = 1L;
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final LocalDateTime CREATIONDATE = LocalDateTime.parse("2021-01-21T12:00:00");
    private static final LocalDateTime EXPIRATIONDATE = LocalDateTime.parse("2023-01-21T12:00:00");
    private static final BigDecimal VALUE = BigDecimal.valueOf(12.50);
    private static final String CPF = "01234567899";
    private static final BigDecimal TOTAL = BigDecimal.valueOf(12.50);
    private final StatusEnum STATUS = StatusEnum.CANCELED;
    private final PaymentStatusEnum PAYMENTSTATUS = PaymentStatusEnum.REJECTED;
    private final PaymentTypeEnum PAYMENTTYPE = PaymentTypeEnum.PIX;


    @BeforeEach
    void SetUp (){
        MockitoAnnotations.openMocks(this);
        inicializeOrders();
    }

    @Test
    void getOrderByIdTest() throws GenericException {
        Mockito.when(orderRepository.findById(ID)).thenReturn(Optional.of(orderEntity));
        Mockito.when(modelMapper.map(orderEntity, OrderDto.class)).thenReturn(orderDto);
        OrderDto response = orderService.getOrder(ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getId(), ID);
    }
    @Test
    void saveOrderTest() throws  GenericException {
        Mockito.when(modelMapper.map(orderDto, OrderEntity.class)).thenReturn(orderEntity);
        Mockito.when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        orderService.addNewOrder(orderDto);

        Assertions.assertEquals(orderDto.getCpf(), orderEntity.getCpf());
    }

    @Test
    void updateOrderTest() throws GenericException {
        Mockito.when(orderRepository.findById(ID)).thenReturn(Optional.of(orderEntity));
        Mockito.when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        String newCpf = "01234567891";
        orderDto.setCpf(newCpf);
        orderService.updateOrderById(ID, orderDto);

        Assertions.assertEquals(orderDto.getCpf(), orderEntity.getCpf());

        // Return to previous cpf
        orderDto.setCpf(CPF);
        orderEntity.setCpf(CPF);
    }

    @Test
    void listOrdersPageTest() throws GenericException {
        PageRequest pagination = PageRequest.of(1, 10);

        List<OrderEntity> order = Arrays.asList(new OrderEntity());
        Page<OrderEntity> ordersPage = new PageImpl<>(order, pagination, order.size());

        List<OrderDto> ordersDto = Arrays.asList(new OrderDto());
        Page<OrderDto> pageDto = new PageImpl<>(ordersDto, pagination, ordersDto.size());

        Mockito.when(orderRepository.findAll(pagination)).thenReturn(ordersPage);
        Mockito.when(mapUtils.mapEntityPageIntoDtoPage(ordersPage, OrderDto.class)).thenReturn(pageDto);

        Page<OrderDto> response = orderService.getAllOrders(null, pagination);
        Assertions.assertTrue(response.getContent().size() == 1);
    }

    @Test
    void listOrdersByFilterCpfTest() throws GenericException {
        PageRequest pagination = PageRequest.of(1, 10);

        List<OrderEntity> orders = Arrays.asList(new OrderEntity());
        orders.get(0).setCpf(CPF);
        Page<OrderEntity> ordersPage = new PageImpl<>(orders, pagination, orders.size());

        List<OrderDto> ordersDto = Arrays.asList(new OrderDto());
        ordersDto.get(0).setCpf(CPF);
        Page<OrderDto> pageDto = new PageImpl<>(ordersDto, pagination, ordersDto.size());

        Mockito.when(orderRepository.findByCpf(CPF, pagination)).thenReturn(ordersPage);
        Mockito.when(mapUtils.mapEntityPageIntoDtoPage(ordersPage, OrderDto.class)).thenReturn(pageDto);

        Page<OrderDto> response = orderService.getAllOrders(CPF, pagination);
        Assertions.assertTrue(response.getContent().size() == 1);
        Assertions.assertEquals(response.getContent().get(0).getCpf(), CPF);
    }

    @Test
    void deleteOrderIdTest() throws GenericException {
        Mockito.when(orderRepository.findById(ID)).thenReturn(Optional.of(orderEntity));
        orderService.deleteOrderById(ID);
        Mockito.verify(orderRepository).delete(orderEntity);
    }

    private void inicializeOrders(){

        OrderPaymentDto paymentDto = OrderPaymentDto.builder().id(1L)
                .cardName("JOAO SILVA").cardNumber("34343").brand(BrandEnum.MASTERCARD)
                .currency(CurrencyEnum.BRL).expirationMonth(ExpirationMonthEnum.AUG).expirationYear("25").securityCode("598").
                value(BigDecimal.valueOf(45.90)).build();

        OrderPaymentEntity paymentEntity = OrderPaymentEntity.builder().id(1L)
                .cardName("JOAO SILVA").cardNumber("34343").brand(BrandEnum.MASTERCARD)
                .currency(CurrencyEnum.BRL).expirationMonth(ExpirationMonthEnum.AUG).expirationYear("25").securityCode("598").
                value(BigDecimal.valueOf(45.90)).build();

        orderItemDto = new OrderItemDto(ID, NAME, CREATIONDATE, EXPIRATIONDATE, VALUE, DESCRIPTION, null);
        listDto = new ArrayList<>();
        listDto.add(orderItemDto);
        orderDto = new OrderDto(ID, CPF, listDto, TOTAL, STATUS, PAYMENTSTATUS, PAYMENTTYPE, paymentDto);

        orderItemEntity = new OrderItemEntity(ID, NAME, CREATIONDATE, EXPIRATIONDATE, VALUE, DESCRIPTION, null);
        listEntity  = new ArrayList<>();
        listEntity.add(orderItemEntity);
        orderEntity = new OrderEntity(ID, CPF, listEntity, TOTAL, STATUS, PAYMENTSTATUS, PAYMENTTYPE, paymentEntity);
    }
}
