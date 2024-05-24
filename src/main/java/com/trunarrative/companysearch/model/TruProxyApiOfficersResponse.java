package com.trunarrative.companysearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TruProxyApiOfficersResponse {

    List<Officer> items;
}
