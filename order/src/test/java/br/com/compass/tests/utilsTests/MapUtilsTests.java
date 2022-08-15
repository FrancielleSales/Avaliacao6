package br.com.compass.tests.utilsTests;

import br.com.compass.order.dtos.OrderDto;
import br.com.compass.order.entities.OrderEntity;
import br.com.compass.order.utils.MapUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class MapUtilsTests {

    @InjectMocks
    private MapUtils mapUtils;

    @Mock
    ModelMapper modelMapper;

    @Test
   void mapEntityPageIntoDtoPageTest () {
        PageRequest pagination = PageRequest.of(1, 10);
        List<OrderEntity> order = Arrays.asList(new OrderEntity());
        Page<OrderEntity> ordersPage = new PageImpl<>(order, pagination, order.size());

        Page<OrderDto> response = mapUtils.mapEntityPageIntoDtoPage(ordersPage, OrderDto.class);

        Assertions.assertNotNull(response);
    }
}
