package eu.isakels.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CreatePaymentResp {

    private final String id;

    @JsonCreator
    public CreatePaymentResp(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
