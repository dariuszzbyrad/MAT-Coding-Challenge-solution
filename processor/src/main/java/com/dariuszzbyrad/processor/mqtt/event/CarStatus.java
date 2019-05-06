package com.dariuszzbyrad.processor.mqtt.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarStatus {
    @JsonProperty(value="timestamp")
    private long timestamp;

    @JsonProperty(value="carIndex")
    private int carIndex;

    @JsonProperty(value="type")
    private String type;

    @JsonProperty(value="value")
    private int value;
}
