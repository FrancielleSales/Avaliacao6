package br.com.compass.site.services;

import br.com.compass.site.advices.GenericException;
import br.com.compass.site.dtos.checkout.CheckoutDto;
import br.com.compass.site.dtos.orderRequest.OrderDto;
import br.com.compass.site.dtos.orderRequest.OrderItemDto;
import br.com.compass.site.dtos.orderRequest.OrderPaymentDto;
import br.com.compass.site.dtos.orderResponse.OrderResponseDto;
import br.com.compass.site.entities.CardsEntity;
import br.com.compass.site.entities.ClientsEntity;
import br.com.compass.site.entities.ItemsEntity;
import br.com.compass.site.entities.checkout.CheckoutEntity;
import br.com.compass.site.enums.CurrencyEnum;
import br.com.compass.site.enums.PaymentTypeEnum;
import br.com.compass.site.producers.CheckoutProducer;
import br.com.compass.site.repositories.CheckoutRepository;
import br.com.compass.site.repositories.ClientsRepository;
import br.com.compass.site.repositories.ItemsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Slf4j
@Service
public class CheckoutService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private CheckoutProducer checkoutProducer;

    @Autowired
    private WebRequest webRequest;

    public OrderResponseDto addNewCheckout(CheckoutDto checkoutDto) throws GenericException {
        log.info("addNewCheckout() - START - Saving checkout");

        // Check clientId existence
        if(!clientsRepository.existsById(checkoutDto.getClientsCheckout().getClientId())) {
            throw new GenericException("This client is not registered!");
        }

        // Check card existence
        final int cardId = parseInt(checkoutDto.getClientsCheckout().getCardId());
        ClientsEntity clientsEntity = clientsRepository.findById(checkoutDto.getClientsCheckout().getClientId()).get();

        if (cardId > clientsEntity.getCards().size() || cardId == 0) {
            throw new GenericException("This customer does not have this card registered!");
        }

        // Variable for total amount
        BigDecimal totalValue = BigDecimal.valueOf(0);

        for(int i = 0; i < checkoutDto.getItemsCheckout().size(); i++ ){
            String skuId = checkoutDto.getItemsCheckout().get(i).getSkuId();

            // Check skuId existence
            int qtd = checkoutDto.getItemsCheckout().get(i).getQtd();
            if (!itemsRepository.existsBySkuId(skuId)){
                throw new GenericException("This skuId " + skuId + " is not registered!");
            }

            // Check stock
            Long qtdStock = itemsRepository.findBySkuId(skuId).getStock();
            if (qtd > qtdStock) {
                throw new GenericException(skuId + " with insufficient stock - Current stock = " + qtdStock);
            }

            // Total order amount
            BigDecimal value = itemsRepository.findBySkuId(skuId).getValue();
            totalValue = totalValue.add(BigDecimal.valueOf(qtd).multiply(value));
        }

        // Update stock e items list creation
        List<OrderItemDto> orderItemDto = new ArrayList<>();
        for(int i = 0; i < checkoutDto.getItemsCheckout().size(); i++ ) {
            String skuId = checkoutDto.getItemsCheckout().get(i).getSkuId();
            ItemsEntity itemsEntity = itemsRepository.findBySkuId(skuId);
            int qtd = checkoutDto.getItemsCheckout().get(i).getQtd();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String creationDate = itemsEntity.getCreationDate().format(dateTimeFormatter);
            String expirationDate = itemsEntity.getCreationDate().format(dateTimeFormatter);

            OrderItemDto item = OrderItemDto.builder()
                    .name(itemsEntity.getName())
                    .creationDate(creationDate)
                    .expirationDate(expirationDate)
                    .qtd(qtd)
                    .value(itemsEntity.getValue())
                    .description(itemsEntity.getDescription())
                    .build();

            orderItemDto.add(item);

            itemsEntity.setStock(itemsEntity.getStock() - qtd);
            itemsRepository.save(itemsEntity);
        }

        CardsEntity card = clientsEntity.getCards().get(cardId);
        OrderPaymentDto orderPaymentDto = OrderPaymentDto.builder()
                .cardName(card.getCardName())
                .cardNumber(card.getCardNumber())
                .securityCode(card.getSecurityCode())
                .brand(card.getBrand())
                .expirationMonth(card.getExpirationMonth())
                .expirationYear(card.getExpirationYear())
                .currency(CurrencyEnum.BRL)
                .value(totalValue)
                .build();

        OrderDto orderDto = OrderDto.builder()
                .cpf(checkoutDto.getClientsCheckout().getClientId())
                .items(orderItemDto)
                .total(totalValue)
                .payment(orderPaymentDto)
                .paymentType(PaymentTypeEnum.CREDIT_CARD)
                .build();

        return webRequest.sendOrder(orderDto);
    }
}
