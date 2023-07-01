package com.mdaq.testing;

import java.io.IOException;

public class TeamsReportSender {

    public static void main(String[] args) throws IOException, InterruptedException {
        GitlabService service = new GitlabService();
        TeamsWebhookService teamsWebhookService = new TeamsWebhookService();
        var latestTestReport = service.getLatestTestReport(service.getUrl());
        teamsWebhookService.notifyTeam();

    }
}
