package com.mdaq.testing.reportportal;

import com.mdaq.testing.GsonHandler;
import com.mdaq.testing.HttpClientHandler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ReportPortalService {
    private static final String GET_LAUNCH_UUID_ENDPOINT = "/api/v1/%s/launch/uuid/%s";
    public GetLaunchByUuidResponse getLaunchByUuid(URI uri) throws IOException, InterruptedException {
        System.out.println(uri.toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + System.getenv("REPORT_PORTAL_TOKEN"))
                .build();
        var response = HttpClientHandler.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
        return GsonHandler.getGson().fromJson(response.body(), GetLaunchByUuidResponse.class);
    }

    public URI getUrl(ReportPortalLaunchInfo launchInfo){
        return URI.create(launchInfo.getBaseUrl() + String.format(GET_LAUNCH_UUID_ENDPOINT, launchInfo.getProjectName(), launchInfo.getLaunchUuid()));
    }
}
