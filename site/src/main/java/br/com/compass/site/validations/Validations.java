package br.com.compass.site.validations;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class Validations {

    public static boolean validateCpf(String cpf) {
        if(!cpf.matches("^\\d{11}+$")){
            return false;
        }
        return true;
    }

    public static boolean validateSecurityCode(String securityCode) {
        if(!securityCode.matches("^\\d{3}+$")){
            return false;
        }
        return true;
    }

    /*

    public static boolean validateDateItems(List <OrderItemEntity> orderItemEntity) {

        for (int i = 0; i < orderItemEntity.size(); i++) {

            OrderItemEntity item = orderItemEntity.get(i);

            LocalDateTime creationDate = item.getCreationDate();
            LocalDateTime expirationDate = item.getExpirationDate();
            if (!creationDate.isBefore(expirationDate) || creationDate.isEqual(expirationDate)) {
                return false;
            }

        }

        return true;
    }

    //
    public static boolean validateDateOffers(List <OrderItemEntity> orderItemEntity) {

        for (int i = 0; i < orderItemEntity.size(); i++) {

            OrderItemOfferEntity offer = orderItemEntity.get(i).getOffer();

            if(offer != null) {
                LocalDateTime creationDate = offer.getCreationDate();
                LocalDateTime expirationDate = offer.getExpirationDate();

                if (!creationDate.isBefore(expirationDate) | creationDate.isEqual(expirationDate)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean validateExpirationOffers(List <OrderItemEntity> orderItemEntity) {

        for (int i = 0; i < orderItemEntity.size(); i++) {

            OrderItemOfferEntity offer = orderItemEntity.get(i).getOffer();

            if(offer != null) {
                LocalDateTime expirationDate = offer.getExpirationDate();
                LocalDateTime currentDate = LocalDateTime.now();

                if (expirationDate.isBefore(currentDate)| expirationDate.isEqual(currentDate)) {
                    return true;
                }
            }
        }

        return false;
    }
     */
}
