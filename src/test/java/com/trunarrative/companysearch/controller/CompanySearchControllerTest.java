package com.trunarrative.companysearch.controller;

import com.trunarrative.companysearch.model.CompanySearchRequest;
import com.trunarrative.companysearch.model.CompanySearchResponse;
import com.trunarrative.companysearch.service.TruProxyApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanySearchControllerTest {

    @InjectMocks
    private CompanySearchController companySearchController;

    @Mock
    private TruProxyApiService truProxyApiService;

    @Test
    void shouldReturnACompanySearchResponse() {

        var activeCompanies = true;
        var apiKey = "xxxxxxxx";
        var companySearchRequest = CompanySearchRequest.builder()
                .companyName("companyName")
                .companyNumber("companyNumber")
                .build();
        when(truProxyApiService.getCompaniesAndOfficers(anyString(), anyString(), anyBoolean(), anyString())).thenReturn(new CompanySearchResponse());

        var companySearchResponse = companySearchController
                .companySearch(companySearchRequest, activeCompanies, apiKey);

        assertThat(companySearchResponse).isNotNull();
        assertThat(companySearchResponse).isInstanceOf(ResponseEntity.class);
        assertThat(companySearchResponse.getBody()).isInstanceOf(CompanySearchResponse.class);
    }
}