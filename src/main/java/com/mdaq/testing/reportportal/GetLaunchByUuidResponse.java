package com.mdaq.testing.reportportal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetLaunchByUuidResponse {
    private String status;
    private Statistics statistics;
    private float approximateDuration;
    private String name;
    private int number;
    private long startTime;


}
