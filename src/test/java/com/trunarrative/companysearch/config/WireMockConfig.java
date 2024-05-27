package com.trunarrative.companysearch.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@TestComponent //@Slf4j
public class WireMockConfig {

    private static final String COMPANIES_URL = "/TruProxyAPI/rest/Companies/v1/Search";
    private static final String OFFICERS_URL = "/TruProxyAPI/rest/Companies/v1/Officers";

    private WireMockServer wireMockServer;

    @Value("${wiremock.server.port}")
    private int wireMockPort;

    @PostConstruct
    public void setup() throws IOException {
        wireMockServer = new WireMockServer(wireMockConfig().port(wireMockPort));
        wireMockServer.start();
        System.out.println("WireMock server started on port " + wireMockPort);

        configureFor("localhost", wireMockPort);

        stubFor(get(urlEqualTo(COMPANIES_URL + "?Query=companyName"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(Files.readString(Paths.get("src/test/resources/stubs/company-search-response.json")))));

        stubFor(get(urlEqualTo(OFFICERS_URL + "?CompanyNumber=12345678"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(Files.readString(Paths.get("src/test/resources/stubs/company-officers-response.json")))));
    }

    @PreDestroy
    public void cleanUp() {
        System.out.println("Stopping WireMock server");
        wireMockServer.stop();
        System.out.println("WireMock server stopped");

    }
}