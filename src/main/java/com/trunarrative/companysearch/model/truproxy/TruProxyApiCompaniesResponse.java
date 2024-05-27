package com.trunarrative.companysearch.model.truproxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trunarrative.companysearch.model.Company;
import lombok.Data;

import java.util.List;

@Data
public class TruProxyApiCompaniesResponse {

    @JsonProperty("total_results")
    private int totalResults;
    private List<Company> items;
}
