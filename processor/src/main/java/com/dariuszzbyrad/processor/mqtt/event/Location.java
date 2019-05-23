package com.dariuszzbyrad.processor.mqtt.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {

    public static Location EMPTY;

    @JsonProperty(value="lat")
    private double lat;

    @JsonProperty(value="long")
    private double lon;
}
