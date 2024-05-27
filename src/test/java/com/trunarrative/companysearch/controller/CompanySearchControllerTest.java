package com.trunarrative.companysearch.controller;

import com.trunarrative.companysearch.model.Company;
import com.trunarrative.companysearch.model.requestresponse.CompanySearchRequest;
import com.trunarrative.companysearch.model.requestresponse.CompanySearchResponse;
import com.trunarrative.companysearch.repository.CompanyRepository;
import com.trunarrative.companysearch.service.TruProxyApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanySearchControllerTest {

    @Mock
    private TruProxyApiService truProxyApiService;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanySearchController companySearchController;

    @Test
    void shouldUseCompanyNameForTruProxyApiQueryIfCompanyNumberNotSupplied() {

        var activeCompanies = true;
        var apiKey = "*** secret-api-key ***";
        var companySearchRequest = CompanySearchRequest.builder()
                .companyName("companyName")
                .companyNumber(null)
                .build();

        when(truProxyApiService.getCompaniesAndOfficers(anyString(), any(), anyBoolean(), anyString())).thenReturn(CompanySearchResponse.builder().build());

        companySearchController.companySearch(companySearchRequest, activeCompanies, apiKey);

        verify(truProxyApiService, times(1)).getCompaniesAndOfficers("companyName", null, true, "*** secret-api-key ***");
    }

    @Test
    void shouldUseCompanyNumberForTruProxyApiQuery() {

        var activeCompanies = true;
        var apiKey = "*** secret-api-key ***";
        var companySearchRequest = CompanySearchRequest.builder()
                .companyName(null)
                .companyNumber("companyNumber")
                .build();

        when(truProxyApiService.getCompaniesAndOfficers(any(), anyString(), anyBoolean(), anyString())).thenReturn(CompanySearchResponse.builder().build());
        when(companyRepository.findByCompanyNumber("companyNumber")).thenReturn(null);

        companySearchController.companySearch(companySearchRequest, activeCompanies, apiKey);

        verify(truProxyApiService, times(1)).getCompaniesAndOfficers(null, "companyNumber", true, "*** secret-api-key ***");
    }

    @Test
    void shouldReturnCompanyFromDatabaseIfFound() {

        var activeCompanies = true;
        var apiKey = "*** secret-api-key ***";
        var companySearchRequest = CompanySearchRequest.builder()
                .companyName(null)
                .companyNumber("companyNumber")
                .build();

        var company = new Company();
        when(companyRepository.findByCompanyNumber("companyNumber")).thenReturn(company);

        var response = companySearchController.companySearch(companySearchRequest, activeCompanies, apiKey);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotalResults()).isEqualTo(1);
        assertThat(response.getBody().getItems()).isNotNull();
        assertThat(response.getBody().getItems().size()).isEqualTo(1);
        assertThat(response.getBody().getItems().get(0)).isEqualTo(company);
    }

    @Test
    void shouldPrioritizeCompanyNumberOverCompanyName() {
        var activeCompanies = true;
        var apiKey = "*** secret-api-key ***";
        var companySearchRequest = CompanySearchRequest.builder()
                .companyName("companyName")
                .companyNumber("companyNumber")
                .build();

        var company = new Company();
        when(companyRepository.findByCompanyNumber("companyNumber")).thenReturn(company);

        var response = companySearchController.companySearch(companySearchRequest, activeCompanies, apiKey);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotalResults()).isEqualTo(1);
        assertThat(response.getBody().getItems()).isNotNull();
        assertThat(response.getBody().getItems().size()).isEqualTo(1);
        assertThat(response.getBody().getItems().get(0)).isEqualTo(company);
        verify(truProxyApiService, never()).getCompaniesAndOfficers(anyString(), anyString(), anyBoolean(), anyString());
    }

    @Test
    void shouldNotCallApiOrDatabaseWhenNoCompanyNameOrNumber() {
        var activeCompanies = true;
        var apiKey = "*** secret-api-key ***";
        var companySearchRequest = CompanySearchRequest.builder()
                .companyName(null)
                .companyNumber(null)
                .build();

        var response = companySearchController.companySearch(companySearchRequest, activeCompanies, apiKey);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotalResults()).isEqualTo(0);
        assertThat(response.getBody().getItems()).isEmpty();
        verify(truProxyApiService, never()).getCompaniesAndOfficers(anyString(), anyString(), anyBoolean(), anyString());
        verify(companyRepository, never()).findByCompanyNumber(anyString());
    }

    @Test
    void shouldHandleNullApiKey() {
        var activeCompanies = true;
        String apiKey = null;
        var companySearchRequest = CompanySearchRequest.builder()
                .companyName("companyName")
                .companyNumber("companyNumber")
                .build();

        var response = companySearchController.companySearch(companySearchRequest, activeCompanies, apiKey);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotalResults()).isEqualTo(0);
        assertThat(response.getBody().getItems()).isEmpty();
        verify(truProxyApiService, never()).getCompaniesAndOfficers(anyString(), anyString(), anyBoolean(), anyString());
    }

    @Test
    void shouldHandleNullCompanySearchRequest() {
        var activeCompanies = true;
        var apiKey = "*** secret-api-key ***";
        CompanySearchRequest companySearchRequest = null;

        var response = companySearchController.companySearch(companySearchRequest, activeCompanies, apiKey);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotalResults()).isEqualTo(0);
        assertThat(response.getBody().getItems()).isEmpty();
        verify(truProxyApiService, never()).getCompaniesAndOfficers(anyString(), anyString(), anyBoolean(), anyString());
        verify(companyRepository, never()).findByCompanyNumber(anyString());
    }

    @Test
    void shouldHandleExceptionFromCompanyRepositoryAndCallTruProxyApi() {
        var activeCompanies = true;
        var apiKey = "*** secret-api-key ***";
        var companySearchRequest = CompanySearchRequest.builder()
                .companyName(null)
                .companyNumber("companyNumber")
                .build();

        when(companyRepository.findByCompanyNumber("companyNumber")).thenThrow(new RuntimeException("Test exception"));
        when(truProxyApiService.getCompaniesAndOfficers(any(), anyString(), anyBoolean(), anyString())).thenReturn(CompanySearchResponse.builder().totalResults(0).items(List.of()).build());

        var response = companySearchController.companySearch(companySearchRequest, activeCompanies, apiKey);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotalResults()).isEqualTo(0);
        assertThat(response.getBody().getItems()).isEmpty();
        verify(truProxyApiService, times(1)).getCompaniesAndOfficers(null, "companyNumber", true, "*** secret-api-key ***");
    }
}