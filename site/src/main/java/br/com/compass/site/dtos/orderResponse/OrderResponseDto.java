package br.com.compass.site.dtos.orderResponse;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderResponseDto {
    Long id;
    BigDecimal total;
    String status;
    List<OrderItensResponseDto> itens;
}
