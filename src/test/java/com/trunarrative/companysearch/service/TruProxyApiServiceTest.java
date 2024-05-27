package com.trunarrative.companysearch.service;

import com.trunarrative.companysearch.model.*;
import com.trunarrative.companysearch.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TruProxyApiServiceTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TruProxyApiService truProxyApiService;

    @Test
    void shouldGetActiveCompanyAndWorkingOfficersFromTruProxyApiGivenCompanyNumberAndActiveTrue() {

        var company = new Company();
        company.setCompanyNumber("12345678");
        company.setCompanyStatus("active");

        var companies = new ArrayList<Company>();
        companies.add(company);

        var truProxyApiCompaniesResponse = new TruProxyApiCompaniesResponse();
        truProxyApiCompaniesResponse.setTotalResults(companies.size());
        truProxyApiCompaniesResponse.setItems(companies);

        var officer = new TruProxyOfficer();
        var officers = new ArrayList<TruProxyOfficer>();
        officers.add(officer);

        var truProxyApiOfficersResponse = new TruProxyApiOfficersResponse();
        truProxyApiOfficersResponse.setItems(officers);

        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(TruProxyApiCompaniesResponse.class)))
                .thenReturn(ResponseEntity.ok(truProxyApiCompaniesResponse));
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(TruProxyApiOfficersResponse.class)))
                .thenReturn(ResponseEntity.ok(truProxyApiOfficersResponse));

        CompanySearchResponse companySearchResponse = truProxyApiService
                .getCompaniesAndOfficers(null, "12345678", true, "*** secret-api-key ***");

        assertThat(companySearchResponse.getTotalResults()).isEqualTo(1);
        assertThat(companySearchResponse.getItems()).hasSize(1);
        assertThat(companySearchResponse.getItems().get(0).getOfficers()).hasSize(1);
        verify(restTemplateBuilder, times(2)).build();
        verify(companyRepository).saveAll(companies);
    }

    @Test
    void shouldGetNoCompanyFromTruProxyApiGivenCompanyNumberIfActiveTruePassedAndCompanyNotActive() {

        var company = new Company();
        company.setCompanyNumber("10241297");
        company.setCompanyStatus("dissolved");

        var companies = new ArrayList<Company>();
        companies.add(company);

        var truProxyApiCompaniesResponse = new TruProxyApiCompaniesResponse();
        truProxyApiCompaniesResponse.setTotalResults(companies.size());
        truProxyApiCompaniesResponse.setItems(companies);

        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(TruProxyApiCompaniesResponse.class)))
                .thenReturn(ResponseEntity.ok(truProxyApiCompaniesResponse));

        CompanySearchResponse companySearchResponse = truProxyApiService
                .getCompaniesAndOfficers(null, "10241297", true, "*** secret-api-key ***");

        assertThat(companySearchResponse.getTotalResults()).isEqualTo(0);
        assertThat(companySearchResponse.getItems()).isEmpty();
        verify(restTemplateBuilder).build();
        verifyNoInteractions(companyRepository);
    }

    @Test
    void shouldGetCompanyFromTruProxyApiGivenCompanyNumberIfActiveFalsePassedAndCompanyNotActive() {

        var company = new Company();
        company.setCompanyNumber("10241297");
        company.setCompanyStatus("dissolved");

        var companies = new ArrayList<Company>();
        companies.add(company);

        var truProxyApiCompaniesResponse = new TruProxyApiCompaniesResponse();
        truProxyApiCompaniesResponse.setTotalResults(companies.size());
        truProxyApiCompaniesResponse.setItems(companies);

        var officer = new TruProxyOfficer();
        var officers = new ArrayList<TruProxyOfficer>();
        officers.add(officer);

        var truProxyApiOfficersResponse = new TruProxyApiOfficersResponse();
        truProxyApiOfficersResponse.setItems(officers);

        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(TruProxyApiCompaniesResponse.class)))
                .thenReturn(ResponseEntity.ok(truProxyApiCompaniesResponse));

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(TruProxyApiOfficersResponse.class)))
                .thenReturn(ResponseEntity.ok(truProxyApiOfficersResponse));

        CompanySearchResponse companySearchResponse = truProxyApiService
                .getCompaniesAndOfficers(null, "10241297", false, "*** secret-api-key ***");

        assertThat(companySearchResponse.getTotalResults()).isEqualTo(1);
        assertThat(companySearchResponse.getItems()).hasSize(1);
        assertThat(companySearchResponse.getItems().get(0).getOfficers()).hasSize(1);
        verify(restTemplateBuilder, times(2)).build();
        verify(companyRepository).saveAll(companies);
    }

//    @Test
//    void shouldGetMultipleCompaniesGivenCompanyNameAndActiveFalse() {
//
//        var company1 = new Company();
//
//        company1.setCompanyName("BBC1");
//        company1.setCompanyStatus("active");
//
//        var company2 = new Company();
//        company2.setCompanyName("BBC2");
//        company2.setCompanyStatus("dissolved");
//
//        var companies = new ArrayList<Company>();
//        companies.add(company1);
//        companies.add(company2);
//
//        var truProxyApiCompaniesResponse = new TruProxyApiCompaniesResponse();
//        truProxyApiCompaniesResponse.setTotalResults(companies.size());
//        truProxyApiCompaniesResponse.setItems(companies);
//
//        var officer = new TruProxyOfficer();
//        var officers = new ArrayList<TruProxyOfficer>();
//        officers.add(officer);
//
//        var truProxyApiOfficersResponse = new TruProxyApiOfficersResponse();
//        truProxyApiOfficersResponse.setItems(officers);
//
//        when(restTemplateBuilder.build()).thenReturn(restTemplate);
//        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(TruProxyApiCompaniesResponse.class)))
//                .thenReturn(ResponseEntity.ok(truProxyApiCompaniesResponse));
//
//        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(TruProxyApiOfficersResponse.class)))
//                .thenReturn(ResponseEntity.ok(truProxyApiOfficersResponse));
//
//        CompanySearchResponse companySearchResponse = truProxyApiService
//                .getCompaniesAndOfficers("company", null, false, "*** secret-api-key ***");
//
//        assertThat(companySearchResponse.getTotalResults()).isEqualTo(2);
//        assertThat(companySearchResponse.getItems()).hasSize(2);
//        assertThat(companySearchResponse.getItems().get(0).getOfficers()).hasSize(1);
//        assertThat(companySearchResponse.getItems().get(1).getOfficers()).hasSize(1);
//        verify(restTemplateBuilder, times(4)).build();
//        verify(companyRepository).saveAll(companies);
//    }

    //get companies and officers from both active and inactive companies - given company name only no company number
    //get companies and officers excluding resigned officers - company name only and company number
    //get companies and officers from only active companies, exclude non-active companies
    //get companies and officers from only active companies excluding resigned officers
    //get companies and officers from the database if available and do not call the truproxy api
}