package br.com.compass.site.utils;

import br.com.compass.site.dtos.ItemsDto;
import br.com.compass.site.entities.ItemsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.UUID;

@Component
public class SkuIdGeneratorUtil {

    public static String SkuIdGenerator(ItemsEntity itemsEntity) {
        String initials = itemsEntity.getName().toLowerCase().substring(0, 3);
        String numbers = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 18));

        return initials + numbers;

    }
}
