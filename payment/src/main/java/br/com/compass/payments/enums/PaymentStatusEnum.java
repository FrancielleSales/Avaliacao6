package br.com.compass.payments.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum PaymentStatusEnum {

    PROCESSING ("Processando"),
    REJECTED ("Rejeitado"),
    APPROVED ("Aprovado");

    private String paymentStatus;

    PaymentStatusEnum(String paymentStatus) {

        this.paymentStatus = paymentStatus;
    }

    public String returnPaymentStatus() {

        return this.paymentStatus;
    }

    @JsonCreator
    public static PaymentStatusEnum decode(final String paymentStatus) {
        return Stream.of(PaymentStatusEnum.values()).filter(
                targetEnum -> targetEnum.paymentStatus.equals(paymentStatus)
        ).findFirst().orElseThrow(
                () -> new RuntimeException("Invalid value:" + paymentStatus));
    }

    @JsonValue
    public String getPaymentStatus() {

        return paymentStatus;
    }
}
