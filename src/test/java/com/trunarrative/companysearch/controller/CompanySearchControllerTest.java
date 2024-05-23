package com.trunarrative.companysearch.controller;

import com.trunarrative.companysearch.model.CompanySearchResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompanySearchControllerTest {

    @InjectMocks
    private CompanySearchController companySearchController;

    @Test
    void shouldReturnACompanySearchResponse() {
        //Given
        boolean activeCompanies = true;

        //When
        final var companySearchResponse = companySearchController.searchCompany(activeCompanies);

        //Then
        assertThat(companySearchResponse).isNotNull();
        assertThat(companySearchResponse).isInstanceOf(ResponseEntity.class);
        assertThat(companySearchResponse.getBody()).isInstanceOf(CompanySearchResponse.class);
    }
}