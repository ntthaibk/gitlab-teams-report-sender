package com.mdaq.testing.gitlab;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestCaseResponse {
    private String status;
    private String name;
    private String classname;
    private String file;
    @SerializedName("execution_time")
    private String executionTime;
    @SerializedName("system_output")
    private Object systemOutput;
    @SerializedName("stack_trace")
    private String stackTrace;
    @SerializedName("recent_failures")
    private String recentFailures;


}
