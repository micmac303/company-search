package com.trunarrative.companysearch.model.requestresponse;

import com.trunarrative.companysearch.model.Company;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanySearchResponse {

    private int totalResults;
    private List<Company> items;
}

