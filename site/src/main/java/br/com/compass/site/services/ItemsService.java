package br.com.compass.site.services;

import br.com.compass.site.advices.GenericException;
import br.com.compass.site.dtos.ItemsDto;
import br.com.compass.site.entities.ItemsEntity;
import br.com.compass.site.repositories.ItemsRepository;
import br.com.compass.site.utils.MapUtil;
import br.com.compass.site.utils.SkuIdGeneratorUtil;
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
public class ItemsService {

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MapUtil mapUtil;

    // Post item
    public ItemsDto addNewItem(ItemsDto itemsDto) {
        log.info("addNewItem() - START - Saving item with name '{}'", itemsDto.getName());

        ItemsEntity itemsSave = modelMapper.map(itemsDto, ItemsEntity.class);
        itemsSave.setSkuId(SkuIdGeneratorUtil.SkuIdGenerator(itemsSave));
        itemsSave = itemsRepository.save(itemsSave);
        log.info("addNewItem() - END - Item saved with id '{}'", itemsSave.getId());
        return modelMapper.map(itemsSave, ItemsDto.class);
    }

    // Get all items
    public Page<ItemsDto> findAllItems(@RequestParam(required = false) Long id,
                                   @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.ASC)
                                   Pageable pageable) throws GenericException {
        Page<ItemsEntity> page;

        try {
            if (id == null) {
                page = itemsRepository.findAll(pageable);
            } else {
                page = itemsRepository.findById(id, pageable);
            }
        } catch (Exception ex) {
            throw new GenericException(ex.getMessage());
        }

        return mapUtil.mapEntityPageIntoDtoPage(page, ItemsDto.class);
    }

    // Get item by id
    public ItemsDto findItemById(long id) throws GenericException {
        ItemsEntity itemsEntity = itemsRepository.findById(id).orElseThrow(
                () -> new GenericException("No item found with id: " + id));

        return modelMapper.map(itemsEntity, ItemsDto.class);
    }

    // Delete item by id
    public ResponseEntity<Object> deleteItemById(Long id) throws GenericException {
        log.info("deleteItemById() - START - Deleting item by id: {}", id);
        ItemsEntity itemsEntity = itemsRepository.findById(id).orElseThrow(
                () -> new GenericException("No item found with id: " + id));
        itemsRepository.delete(itemsEntity);
        log.info("deleteItemById() - END - Item successfully deleted");
        return new ResponseEntity<>("{\"message\": \"Item successfully deleted!\"}", HttpStatus.OK);
    }

    // Update item by id
    public ResponseEntity<Object> updateItemById(Long id, ItemsDto itemsDto) throws GenericException {
        log.info("updateItemById() - START - Updating item by id: {}", id);
        ItemsEntity itemsEntity = itemsRepository.findById(id).orElseThrow(
                () -> new GenericException("No item found with id: " + id));

        try {
            itemsEntity.setName(itemsDto.getName() != null ? itemsDto.getName() : itemsEntity.getName());
            itemsEntity.setExpirationDate(itemsDto.getExpirationDate() != null ? itemsDto.getExpirationDate() :
                    itemsEntity.getExpirationDate());
            itemsEntity.setDescription(itemsDto.getDescription() != null ? itemsDto.getDescription() :
                    itemsEntity.getDescription());
            itemsEntity.setStock(itemsDto.getStock() != null ? itemsDto.getStock() : itemsEntity.getStock());
            itemsEntity.setDescription(itemsDto.getDescription() != null ? itemsDto.getDescription() :
                    itemsEntity.getDescription());
            itemsRepository.save(itemsEntity);
            log.info("updateItemById() - END - Item successfully updated");
            return new ResponseEntity<>("{\"message\": \"Item successfully updated!\"}", HttpStatus.OK);
        } catch (Exception ex) {
            throw new GenericException(ex.getMessage());
        }
    }
}
