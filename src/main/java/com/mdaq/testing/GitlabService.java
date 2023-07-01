package com.mdaq.testing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitlabService {
    private static final String GET_REPORT_ENDPOINT = "/projects/%s/pipelines/%s/test_report";
    private static final String CI_API_V4_URL = "CI_API_V4_URL";
    private static final String CI_PROJECT_ID = "CI_PROJECT_ID";
    private static final String CI_PIPELINE_ID = "CI_PIPELINE_ID";
    private static final String PRIVATE_TOKEN = "PRIVATE_TOKEN";

    public GetTestReportResponse getLatestTestReport(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("PRIVATE-TOKEN", System.getenv(PRIVATE_TOKEN))
                .build();
        var response = HttpClientHandler.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        return GsonHandler.getGson().fromJson(response.body(), GetTestReportResponse.class);
    }

    public URI getUrl(){
        String baseUrl = System.getenv(CI_API_V4_URL);
        return URI.create(baseUrl + String.format(GET_REPORT_ENDPOINT, System.getenv(CI_PROJECT_ID), System.getenv(CI_PIPELINE_ID)));
    }
}