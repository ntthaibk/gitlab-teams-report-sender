package com.mdaq.testing;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetTestReportResponse {
    @SerializedName("total_time")
    private String totalTime;
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
    @SerializedName("test_suites")
    private List<TestSuiteResponse> testSuites;

}
