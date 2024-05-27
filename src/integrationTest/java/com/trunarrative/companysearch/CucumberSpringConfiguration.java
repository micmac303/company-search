package com.trunarrative.companysearch;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.val;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import org.wiremock.integrations.testcontainers.WireMockContainer;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class CucumberSpringConfiguration {

    private static WireMockContainer externalService() {
        final var container = new WireMockContainer(DockerImageName.parse("wiremock/wiremock:2.35.0"))
                .withMappingFromResource("responses/responses.json");
        container.start();
        WireMock.configureFor(container.getHost(), container.getPort());
        return container;
    }

    @Container
    private static WireMockContainer externalService = externalService();

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry ) {
//        company.search.url=https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Search?Query=
//        company.officers.url=https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=
        val wiremockUrl = "http://" + externalService.getHost() + ":" + externalService.getMappedPort(8080);
        registry.add("company.search.url", () -> wiremockUrl + "/TruProxyAPI/rest/Companies/v1/Search?Query=");
        registry.add("company.officers.url", () -> wiremockUrl + "/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=");
    }

}
