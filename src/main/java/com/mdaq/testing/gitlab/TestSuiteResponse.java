package com.mdaq.testing.gitlab;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TestSuiteResponse {
    private String name;
    @SerializedName("total_time")
    private Float totalTime;
    @SerializedName("total_count")
    private String totalCount;
    @SerializedName("success_count")
    private String successCount;
    @SerializedName("failed_count")
    private String failedCount;
    @SerializedName("skipped_count")
    private String skippedCount;
    @SerializedName("error_count")
    private String errorCount;
    @SerializedName("suite_error")
    private String suiteError;
    @SerializedName("test_cases")
    private List<TestCaseResponse> testCases;

    
}
