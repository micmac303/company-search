package com.trunarrative.companysearch.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanySearchRequest {

    private String companyName;
    private String companyNumber;
}
