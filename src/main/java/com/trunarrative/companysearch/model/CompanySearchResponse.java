package com.trunarrative.companysearch.model;

import lombok.Data;

import java.util.List;

@Data
public class CompanySearchResponse {

    private int totalResults;
    private List<Company> items;
}

