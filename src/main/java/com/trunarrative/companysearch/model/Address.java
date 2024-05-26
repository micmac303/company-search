package com.trunarrative.companysearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {

    private String locality;
    @JsonProperty("postal_code")
    private String postalCode;
    private String premises;
    @JsonProperty("address_line_1")
    private String addressLine1;
    private String country;
}
