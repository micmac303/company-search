package com.trunarrative.companysearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Address {

    private String locality;
    @JsonProperty("postal_code")
    private String postalCode;
    private String premises;
    @JsonProperty("address_line_1")
    private String addressLine1;
    private String country;
}
