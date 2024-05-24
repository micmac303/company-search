package com.trunarrative.companysearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TruProxyApiCompaniesResponse {

    @JsonProperty("total_results")
    private String totalResults;
    private List<Company> items;
}
