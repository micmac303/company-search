package com.trunarrative.companysearch.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @Disabled
class TruProxyApiServiceMockIntegrationTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private TruProxyApiService truProxyApiService;  // TruProxyApiService is not mocked but should be with wiremock

    //Test 1, Compnay number / true / false
    //Test 2, Name and Number / true / false
    //Test 3, Company name (query) / true / false
    // Active companies
    // Not Resigned officers

    @Test
    void getCompaniesShouldReturnASingleCompanyForTrunarrative() {

        var response = truProxyApiService.getCompanies("trunarrative", "06500244", true, "API-KEY");

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

        var response = truProxyApiService.getCompanies("bbc", "12345123", true,"API-KEY");

        assertThat(response).isNotNull();
        assertThat(response.getItems()).isNotEmpty();
        assertThat(response.getItems().size()).isGreaterThan(1);
        assertThat(response.getItems().size()).isEqualTo(20);
    }

    @Test
    void getOfficersShouldReturnOfficersForTrunarrative() {

        var response = truProxyApiService.getOfficers("10241297", "API-KEY");

        assertThat(response.getItems()).isNotNull();
        assertThat(response.getItems().size()).isGreaterThan(1);
        assertThat(response.getItems().size()).isEqualTo(8);
    }
}