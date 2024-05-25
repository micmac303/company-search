package com.trunarrative.companysearch.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @Disabled
class TruProxyApiServiceIntegrationTest {

    @Autowired
    private TruProxyApiService truProxyApiService;

    @Test
    void getCompaniesShouldReturnASingleCompanyForTrunarrative() {

        var response = truProxyApiService.getCompaniesAndOfficers("trunarrative", "06500244", true, "API-KEY");

        assertThat(response).isNotNull();
        assertThat(response.getItems()).isNotEmpty();
        assertThat(response.getItems().get(0)).isNotNull();
        assertThat(response.getItems().get(0).getCompanyNumber()).isNotEmpty();
        assertThat(response.getItems().get(0).getCompanyStatus()).isEqualTo("dissolved");
        assertThat(response.getItems().get(0).getAddress()).isNotNull();
        assertThat(response.getItems().get(0).getAddress().getPremises()).isEqualTo("Global Reach");
    }

    @Test
    void getCompaniesShouldReturnMultipleCompaniesForBbc() {

        var response = truProxyApiService.getCompaniesAndOfficers("bbc", "12345123",true, "API-KEY");

        assertThat(response).isNotNull();
        assertThat(response.getItems()).isNotEmpty();
        assertThat(response.getItems().size()).isGreaterThan(1);
        assertThat(response.getItems().size()).isEqualTo(20);
    }

    @Test
    void getOfficersShouldReturnOfficersForTrunarrative() {

        var response = truProxyApiService.getCompaniesAndOfficers("acme", "10241297", true, "API-KEY");

        assertThat(response.getItems()).isNotNull();
        assertThat(response.getItems().size()).isGreaterThan(1);
        assertThat(response.getItems().size()).isEqualTo(8);
    }
}