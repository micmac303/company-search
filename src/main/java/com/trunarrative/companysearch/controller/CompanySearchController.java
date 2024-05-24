package com.trunarrative.companysearch.controller;

import com.trunarrative.companysearch.model.CompanySearchRequest;
import com.trunarrative.companysearch.model.CompanySearchResponse;
import com.trunarrative.companysearch.service.TruProxyApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanySearchController {

    private final TruProxyApiService truProxyApiService;

    @PostMapping("/company-search")
    public ResponseEntity<CompanySearchResponse> companySearch(
            @RequestBody CompanySearchRequest companySearchRequest, @RequestParam boolean activeCompanies, @RequestParam String apiKey) {

        var companySearchResponse = truProxyApiService.getCompaniesAndOfficers(
                companySearchRequest.getCompanyName(), companySearchRequest.getCompanyNumber(), activeCompanies, apiKey);

        return ResponseEntity.ok(companySearchResponse);
    }
}
