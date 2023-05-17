package com.gridnt.pdms.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class CommSta_queryDate {
    String dateType;
    String date;
    @JsonProperty("histype")
    String histype;
    @JsonProperty("hiscode")
    String hiscode;

}
