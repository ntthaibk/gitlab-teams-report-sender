package com.mdaq.testing.reportportal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportPortalLaunchInfo {
    private String baseUrl;
    private String projectName;
    private String launchUuid;

}
