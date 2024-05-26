package com.trunarrative.companysearch.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanySearchResponse {

    private int totalResults;
    private List<Company> items;
}

