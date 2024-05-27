package com.trunarrative.companysearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TruProxyApiCompaniesResponse {

    @JsonProperty("total_results")
    private int totalResults;
    private List<Company> items;
}
