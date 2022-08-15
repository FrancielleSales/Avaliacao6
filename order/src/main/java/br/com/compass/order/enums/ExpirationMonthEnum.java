package br.com.compass.order.enums;

import br.com.compass.order.advices.GenericException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum ExpirationMonthEnum {

        JAN ("1"),
        FEB ("2"),
        MAR ("3"),
        APR ("4"),
        MAY ("5"),
        JUN ("6"),
        JUL ("7"),
        AUG ("8"),
        SEP ("9"),
        OCT ("10"),
        NOV ("11"),
        DEC ("12");

        private String expirationMonth;
        ExpirationMonthEnum(String expirationMonth) {

                this.expirationMonth = expirationMonth;
        }

        public String returnExpirationMonth() {

                return this.expirationMonth;
        }

        @JsonCreator
        public static ExpirationMonthEnum decode(final String expirationMonth) throws GenericException {
                return Stream.of(ExpirationMonthEnum.values()).filter(
                        targetEnum -> targetEnum.expirationMonth.equals(expirationMonth)
                ).findFirst().orElseThrow(
                        () -> new GenericException("Invalid value:" + expirationMonth));
        }

        @JsonValue
        public String getExpirationMonth() {

                return expirationMonth;
        }
}
