package br.com.compass.order.services;

import br.com.compass.order.advices.GenericException;
import br.com.compass.order.dtos.OrderItemDto;
import br.com.compass.order.entities.OrderItemEntity;
import br.com.compass.order.repositories.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Update item by id using patch
    public void updateItemById(Long id, OrderItemDto item) throws GenericException {
        log.info("updateItemById() - START - Updating the item by ID: {}", id);

        OrderItemEntity orderItemEntity = orderItemRepository.findById(id).orElseThrow(
                () -> new GenericException("No item found with id " + id));
        if(item.getCreationDate() != null || item.getExpirationDate() != null){
            throw new GenericException("The system does not allow updating dates.");
        }

        try {
            orderItemEntity.setName(item.getName() != null ? item.getName() : orderItemEntity.getName());
            orderItemEntity.setValue(item.getValue() != null ? item.getValue() : orderItemEntity.getValue());
            orderItemEntity.setDescription(item.getDescription() != null ? item.getDescription() : orderItemEntity.getDescription());
            orderItemRepository.save(orderItemEntity);
            log.info("updateItemById() - END - Item successfully updated");
        }catch (Exception ex){
            throw new GenericException(ex.getMessage());
        }
    }
}