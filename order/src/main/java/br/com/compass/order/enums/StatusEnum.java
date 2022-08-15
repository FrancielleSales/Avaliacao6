package br.com.compass.order.enums;

import br.com.compass.order.advices.GenericException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum StatusEnum {

    IN_PROGRESS("Em andamento"),
    FINISHED ("Finalizado"),
    CANCELED("Cancelado");

    private String status;

    StatusEnum(String status) {

        this.status = status;
    }

    public String status() {

        return this.status;
    }

    @JsonCreator
    public static StatusEnum decode(final String status) throws GenericException {
        return Stream.of(StatusEnum.values()).filter(
                targetEnum -> targetEnum.status.equals(status)
        ).findFirst().orElseThrow(
                () -> new GenericException("Invalid value:" + status));
    }

    @JsonValue
    public String getStatus() {

        return status;
    }
}
