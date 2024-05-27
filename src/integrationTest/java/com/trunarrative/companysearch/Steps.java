package com.trunarrative.companysearch;

import io.cucumber.java8.En;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class Steps implements En {
    public Steps(MockMvc mockMvc, Context context) {


        When("I have searched for an existing company", () -> {
            final var result = mockMvc.perform(
                    post("/company-search?activeCompanies=true&apiKey=123456")
                            .content("{ \"companyName\" : \"BBC LIMITED\", \"companyNumber\" : \"06500244\" }")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andReturn();
            context.setMvcResult(result);

        });

        Then("the status code should be {int}", (Integer statusCode) -> {
            assertThat(context.getMvcResult().getResponse().getStatus()).isEqualTo(statusCode);
        });
    }
}
