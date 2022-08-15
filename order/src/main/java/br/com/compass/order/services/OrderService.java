package br.com.compass.order.services;

import br.com.compass.order.enums.PaymentStatusEnum;
import br.com.compass.order.enums.StatusEnum;
import br.com.compass.order.advices.GenericException;
import br.com.compass.order.dtos.OrderDto;
import br.com.compass.order.dtos.OrderItemDto;
import br.com.compass.order.entities.OrderEntity;
import br.com.compass.order.repositories.OrderRepository;
import br.com.compass.order.utils.MapUtils;
import br.com.compass.order.validations.Validations;
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

import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Validations itemValidation;

    @Autowired
    private  ModelMapper modelMapper;

    @Autowired
    private MapUtils mapUtils;

    // Post order
    public OrderDto addNewOrder(OrderDto orderDto) throws GenericException {
        log.info("addNewOrder() - START - Saving order '{}'", orderDto.getCpf());

        if (!Validations.validateCpf(orderDto.getCpf())){
            throw new GenericException("The cpf field must be 11 numeric characters");
        }

        if (!Validations.validateSecutityCode(orderDto.getPayment().getSecurityCode())){
            throw new GenericException("The codigoSeguranca field must be 3 numeric characters");
        }

       List<OrderItemDto> items = orderDto.getItems();

        if(items.isEmpty()){
            throw new GenericException("Item list is empty");
        }

        OrderEntity orderSave = modelMapper.map(orderDto, OrderEntity.class);

        if(!Validations.validateDateItems(orderSave.getItems())){
            throw new GenericException("The dataValidade field must be filled in " +
                    "with a date later than the creationDate field");
        }

        if(!Validations.validateDateOffers(orderSave.getItems())){
            throw new GenericException("The dataValidade field must be filled in " +
                    "with a date later than the creationDate field");
        }

        if(Validations.validateExpirationOffers(orderSave.getItems())){
            throw new GenericException("Offer validity has expired");
        }

        orderSave.setStatus(StatusEnum.IN_PROGRESS);
        orderSave.setPaymentStatus(PaymentStatusEnum.PROCESSING);
        orderSave = orderRepository.save(orderSave);
        log.info("addNewOrder() - END - Order saved with id '{}'", orderSave.getId());
        return modelMapper.map(orderSave, OrderDto.class);
    }

    // GetAll and Filter
    public Page<OrderDto> getAllOrders(@RequestParam(required = false) String cpf,
                                          @PageableDefault(size=100, sort = "id", direction = Sort.Direction.ASC)
                                          Pageable pageable) throws GenericException {
        Page<OrderEntity> page;

        try {
            if (cpf == null) {
                page = orderRepository.findAll(pageable);
            } else {
                page = orderRepository.findByCpf(cpf, pageable);
            }
        }catch(Exception ex){
            throw new GenericException(ex.getMessage());
        }

        return mapUtils.mapEntityPageIntoDtoPage(page, OrderDto.class);
    }

    // Get order by id
    public OrderDto getOrder(long id) throws GenericException {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(
                () -> new GenericException("No order found with id: " + id));

        return modelMapper.map(orderEntity, OrderDto.class);
    }

    // Delete order by id
    public ResponseEntity<Object> deleteOrderById(Long id)  throws GenericException {
        log.info("deleteOrderById() - START - Deleting order by id: {}", id);

        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(
                () -> new GenericException("No order found with id: " + id));

        if (orderEntity.getPaymentStatus().equals(PaymentStatusEnum.PROCESSING)) {
            orderRepository.delete(orderEntity);
            log.info("deleteOrderById() - END - Order successfully deleted");
            return new ResponseEntity<>("{\"message\": \"Order succesfully deleted!\"}", HttpStatus.OK);

        } else {
            log.info("Unable to delete an order with status Aprovado or Rejeitado");
            return new ResponseEntity<>("{\"message\": \"Unable to delete an order with status Aprovado or Rejeitado!\"}", HttpStatus.OK);
        }
    }

    // Update order by id using patch
    public ResponseEntity<Object>  updateOrderById(Long id, OrderDto order) throws GenericException {
        log.info("updateOrderById() - START - Updating order by id: {}", id);

        OrderEntity paymentEntity = orderRepository.findById(id).orElseThrow(
                () -> new GenericException("No order found with id: " + id));

        if (Validations.validateCpf(order.getCpf()) == false){
            throw new GenericException("The cpf field must be 11 numeric characters");
        }

        try {
            paymentEntity.setCpf(order.getCpf() != null ? order.getCpf() : paymentEntity.getCpf());
            paymentEntity.setTotal(order.getTotal() != null ? order.getTotal() : paymentEntity.getTotal());
            orderRepository.save(paymentEntity);
            log.info("updateOrderById() - END - Order successfully updated");
            return new ResponseEntity<>("{\"message\": \"Order succesfully updated!\"}", HttpStatus.OK);
        }catch (Exception ex){
            throw new GenericException(ex.getMessage());
        }
    }
}
