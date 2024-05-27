package com.trunarrative.companysearch.service;

import com.trunarrative.companysearch.model.TruProxyApiCompaniesResponse;
import com.trunarrative.companysearch.model.TruProxyApiOfficersResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TruProxyApiServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldGetCompaniesAndOfficers() {
        ResponseEntity<TruProxyApiCompaniesResponse> response = restTemplate.getForEntity(
                "http://localhost:8089/TruProxyAPI/rest/Companies/v1/Search?Query=companyName",
                TruProxyApiCompaniesResponse.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        // Add more assertions as needed

        ResponseEntity<TruProxyApiOfficersResponse> officersResponse = restTemplate.getForEntity(
                "http://localhost:8089/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=12345678",
                TruProxyApiOfficersResponse.class);

        assertThat(officersResponse.getStatusCode().is2xxSuccessful()).isTrue();
        // Add more assertions as needed
    }


    // --- remove ?
//    @Test
//    void getCompaniesShouldReturnASingleCompanyForTrunarrative() {
//
//        var response = truProxyApiService.getCompaniesAndOfficers("trunarrative", "06500244", true, "API-KEY");
//
//        assertThat(response).isNotNull();
//        assertThat(response.getItems()).isNotEmpty();
//        assertThat(response.getItems().get(0)).isNotNull();
//        assertThat(response.getItems().get(0).getCompanyNumber()).isNotEmpty();
//        assertThat(response.getItems().get(0).getCompanyStatus()).isEqualTo("dissolved");
//        assertThat(response.getItems().get(0).getAddress()).isNotNull();
//        assertThat(response.getItems().get(0).getAddress().getPremises()).isEqualTo("Global Reach");
//    }
//
//    @Test
//    void getCompaniesShouldReturnMultipleCompaniesForBbc() {
//
//        var response = truProxyApiService.getCompaniesAndOfficers("bbc", "12345123",true, "API-KEY");
//
//        assertThat(response).isNotNull();
//        assertThat(response.getItems()).isNotEmpty();
//        assertThat(response.getItems().size()).isGreaterThan(1);
//        assertThat(response.getItems().size()).isEqualTo(20);
//    }
//
//    @Test
//    void getOfficersShouldReturnOfficersForTrunarrative() {
//
//        var response = truProxyApiService.getCompaniesAndOfficers("acme", "10241297", true, "API-KEY");
//
//        assertThat(response.getItems()).isNotNull();
//        assertThat(response.getItems().size()).isGreaterThan(1);
//        assertThat(response.getItems().size()).isEqualTo(8);
//    }
}