package com.trunarrative.companysearch.controller;

import com.trunarrative.companysearch.model.CompanySearchResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanySearchController {


    @PostMapping("/company-search")
    public ResponseEntity<CompanySearchResponse> searchCompany(@RequestParam boolean activeCompanies) {

        return ResponseEntity.ok(new CompanySearchResponse());
    }
}
