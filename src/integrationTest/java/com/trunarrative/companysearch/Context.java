package com.trunarrative.companysearch;

import io.cucumber.spring.ScenarioScope;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

@Component
@ScenarioScope
@Data
public class Context {
    private MvcResult mvcResult;
}
