package com.trunarrative.companysearch.model.truproxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trunarrative.companysearch.model.Address;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TruProxyOfficer {

    private String name;
    @JsonProperty("officer_role")
    private String officerRole;
    @JsonProperty("appointed_on")
    private LocalDate appointedOn;
    private Address address;
    @JsonProperty("resigned_on")
    private String resignedOn;
}

