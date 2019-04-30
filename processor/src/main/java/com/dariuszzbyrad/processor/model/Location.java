package com.dariuszzbyrad.processor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {

    @JsonProperty(value="lat")
    private double lat;

    @JsonProperty(value="long")
    private double lon;
}
