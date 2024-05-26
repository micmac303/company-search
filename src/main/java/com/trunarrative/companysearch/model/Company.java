package com.trunarrative.companysearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty("company_number")
    @Column(unique = true)
    private String companyNumber;
    @JsonProperty("company_type")
    private String companyType;
    private String title;
    @JsonProperty("company_status")
    private String companyStatus;
    @JsonProperty("date_of_creation")
    private String dateOfCreation;
    @Embedded
    private Address address;
    @ElementCollection
    private List<Officer> officers;
}
