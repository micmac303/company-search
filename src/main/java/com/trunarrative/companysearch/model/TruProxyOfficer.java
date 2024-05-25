package com.trunarrative.companysearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
