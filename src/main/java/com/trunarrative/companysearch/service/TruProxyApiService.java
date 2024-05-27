package com.trunarrative.companysearch.service;

import com.trunarrative.companysearch.model.*;
import com.trunarrative.companysearch.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.micrometer.common.util.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@RequiredArgsConstructor @Slf4j
public class TruProxyApiService {

    private final RestTemplateBuilder restTemplateBuilder;
    private final CompanyRepository companyRepository;

    private static final String COMPANIES_URL =
            "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Search?Query=";
    private static final String OFFICERS_URL =
            "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=";

    public CompanySearchResponse getCompaniesAndOfficers(String companyName, String companyNumber, boolean activeCompanies, String apiKey) {

        var truProxyApiCompaniesResponse = getCompanies(companyName, companyNumber, apiKey);

        List<Company> companies = truProxyApiCompaniesResponse.getItems() != null ? truProxyApiCompaniesResponse.getItems() : List.of();

        companies = (activeCompanies)
                ? companies.stream().filter(company -> "active".equals(company.getCompanyStatus())).toList()
                : companies;

        companies.forEach(company -> {
            var officersResponse = getOfficers(company.getCompanyNumber(), apiKey);
            var workingTruProxyOfficers = officersResponse.getItems().stream()
                    .filter(officer -> isBlank(officer.getResignedOn())).toList();
            var workingOfficers = workingTruProxyOfficers.stream()
                    .map(truProxyOfficer -> {
                        Officer officer = new Officer();
                        BeanUtils.copyProperties(truProxyOfficer, officer);
                        return officer;
                    }).toList();

            company.setOfficers(workingOfficers);
        });

        if (!companies.isEmpty()) {
            companyRepository.saveAll(companies);
        }

        var companySearchResponse = CompanySearchResponse.builder().build();
        companySearchResponse.setTotalResults(companies.size());
        companySearchResponse.setItems(companies);
        return companySearchResponse;
    }

    private TruProxyApiCompaniesResponse getCompanies(String companyName, String companyNumber, String apiKey) {

        var headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        var httpEntity = new HttpEntity<>(headers);

        var response = isNotBlank(companyNumber)
            ? restTemplateBuilder.build()
                .exchange(COMPANIES_URL + companyNumber,
                            HttpMethod.GET,
                            httpEntity,
                            TruProxyApiCompaniesResponse.class)
            : restTemplateBuilder.build()
                .exchange(COMPANIES_URL + companyName,
                            HttpMethod.GET,
                            httpEntity,
                            TruProxyApiCompaniesResponse.class);

        return response.getBody();
    }

    private TruProxyApiOfficersResponse getOfficers(String companyNumber, String apiKey) {

        var headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        var httpEntity = new HttpEntity<>(headers);

        var response = restTemplateBuilder.build()
                .exchange(OFFICERS_URL + companyNumber,
                            HttpMethod.GET,
                            httpEntity,
                            TruProxyApiOfficersResponse.class);

        return response.getBody();
    }
}
