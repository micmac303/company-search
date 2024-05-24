package com.trunarrative.companysearch.service;

import com.trunarrative.companysearch.model.TruProxyApiCompaniesResponse;
import com.trunarrative.companysearch.model.TruProxyApiOfficersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
@RequiredArgsConstructor
public class TruProxyApiService {

    private final RestTemplateBuilder restTemplateBuilder;

    private static final String COMPANY_SEARCH_URL =
            "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Search?Query=";

    private static final String COMPANY_OFFICERS_URL =
            "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=";

    //Get List of Companies from company name search
    //for each company call officers endpoint and get the list of officers
    //create the response that contains the list of companies and their officers

    public TruProxyApiCompaniesResponse getCompanies(String companyName, String companyNumber) {


        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<TruProxyApiCompaniesResponse> response;
        if (companyNumber != null) {

            response = restTemplateBuilder.build().exchange(
                            COMPANY_SEARCH_URL + companyNumber,
                            HttpMethod.GET,
                            httpEntity,
                            TruProxyApiCompaniesResponse.class);
        }
        else {

            response = restTemplateBuilder.build().exchange(
                            COMPANY_SEARCH_URL + companyName,
                            HttpMethod.GET,
                            httpEntity,
                            TruProxyApiCompaniesResponse.class);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new HttpServerErrorException(response.getStatusCode());
        }
    }

    public TruProxyApiOfficersResponse getOfficers(String companyNumber) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<TruProxyApiOfficersResponse> response =
                restTemplateBuilder.build().exchange(
                        COMPANY_OFFICERS_URL + companyNumber,
                        HttpMethod.GET,
                        httpEntity,
                        TruProxyApiOfficersResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new HttpServerErrorException(response.getStatusCode());
        }
    }
}
