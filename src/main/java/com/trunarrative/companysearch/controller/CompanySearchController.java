package com.trunarrative.companysearch.controller;

import com.trunarrative.companysearch.model.CompanySearchRequest;
import com.trunarrative.companysearch.model.CompanySearchResponse;
import com.trunarrative.companysearch.repository.CompanyRepository;
import com.trunarrative.companysearch.service.TruProxyApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.micrometer.common.util.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isAllBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RestController
@RequiredArgsConstructor @Slf4j
public class CompanySearchController {

    private final TruProxyApiService truProxyApiService;
    private final CompanyRepository companyRepository;

    @PostMapping("/company-search")
    public ResponseEntity<CompanySearchResponse> companySearch(
            @RequestBody CompanySearchRequest companySearchRequest, @RequestParam boolean activeCompanies, @RequestParam String apiKey) {

        if (companySearchRequest == null || isAllBlank(companySearchRequest.getCompanyName(), companySearchRequest.getCompanyNumber()) || isBlank(apiKey)) {
            return ResponseEntity.badRequest().body(CompanySearchResponse.builder().totalResults(0).items(List.of()).build());
        }

        if (isNotBlank(companySearchRequest.getCompanyNumber())) {
            try {
                var company = companyRepository.findByCompanyNumber(companySearchRequest.getCompanyNumber());
                if (company != null) {
                    log.info("Company found in database: {}", company);
                    var companySearchResponse = CompanySearchResponse.builder().totalResults(1).items(List.of(company)).build();
                    return ResponseEntity.ok(companySearchResponse);
                }
            } catch (Exception e) {
                log.error("Error fetching company from repository", e);
                log.info("Call will be made to the TruProxy API");
            }
        }

        log.info("Either no company number supplied or company not returned from the database, calling TruProxy API");
        var companySearchResponse = truProxyApiService.getCompaniesAndOfficers(
                companySearchRequest.getCompanyName(), companySearchRequest.getCompanyNumber(), activeCompanies, apiKey);

        return ResponseEntity.ok(companySearchResponse);
    }
}
