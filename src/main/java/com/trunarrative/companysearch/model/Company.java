package com.trunarrative.companysearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Company {

    @JsonProperty("company_number")
    private String companyNumber;
    @JsonProperty("company_type")
    private String companyType;
    private String title;
    @JsonProperty("company_status")
    private String companyStatus;
    @JsonProperty("date_of_creation")
    private String dateOfCreation;
    private Address address;
    private List<Officer> officers;
}
