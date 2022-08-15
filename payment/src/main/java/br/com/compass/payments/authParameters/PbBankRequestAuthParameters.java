package br.com.compass.payments.authParameters;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PbBankRequestAuthParameters {

    @JsonProperty("client_id")
    private final String clientId = "client_id_francielle";

    @JsonProperty("api_key")
    private final String apiKey = "92369566-0375-4319-bd53-0f9e291edc50";
}
