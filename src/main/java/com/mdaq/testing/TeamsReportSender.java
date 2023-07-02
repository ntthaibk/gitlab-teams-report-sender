package com.mdaq.testing;

import com.mdaq.testing.gitlab.GitlabService;
import com.mdaq.testing.teams.AdaptiveCard;
import com.mdaq.testing.teams.TeamsWebhookService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.time.Instant;

public class TeamsReportSender {
    private static final String CARD_PATH = "src/main/resources/card.json";

    public static void main(String[] args) throws IOException, InterruptedException {
        String rpLaunchUrl;
        if (args[0] == null || args[0].trim().isEmpty()) {
            System.out.println("Please provide a valid path to ReportPortal Launch URL");
            return;
        } else {
            rpLaunchUrl = Files.readString(FileHandler.getFileDirectory(args[0].trim())).trim();
            System.out.println(rpLaunchUrl);
        }

        GitlabService service = new GitlabService();
        TeamsWebhookService teamsWebhookService = new TeamsWebhookService();
        var latestTestReport = service.getLatestTestReport(service.getUrl());
        Instant time = Instant.now();

        var card = TeamsReportSender.class.getClassLoader().getResourceAsStream("card.json");
        if (card == null) {
            throw new IllegalStateException("Failed to load card.json");
        }
        AdaptiveCard adaptiveCard = GsonHandler.getGson().fromJson(new InputStreamReader(card), AdaptiveCard.class);
        latestTestReport.getTestSuites().forEach(
                testSuite -> adaptiveCard.getAttachments().forEach(
                        attachment -> attachment.getContent().getBody().forEach(
                                body -> {
                                    switch (body.getId()) {
                                        case "card-title" -> body.getItems().forEach(
                                                item -> {
                                                    if (item.getId().equals("general-info")) {
                                                        item.getFacts().forEach(
                                                                fact -> {
                                                                    switch (fact.getTitle()) {
                                                                        case "Date" -> fact.setValue(time.toString());
                                                                        case "Environment" ->
                                                                                fact.setValue(System.getenv("ENV"));
                                                                        case "Test Suite" ->
                                                                                fact.setValue(testSuite.getName());
                                                                    }
                                                                }
                                                        );
                                                    }
                                                }
                                        );
                                        case "test-result" -> body.getFacts().forEach(
                                                fact -> {
                                                    switch (fact.getTitle()) {
                                                        case "Total" ->
                                                                fact.setValue(String.valueOf(testSuite.getTotalCount()));
                                                        case "Passed" ->
                                                                fact.setValue(String.valueOf(testSuite.getSuccessCount()));
                                                        case "Failed" ->
                                                                fact.setValue(String.valueOf(testSuite.getFailedCount()));
                                                        case "Skipped" ->
                                                                fact.setValue(String.valueOf(testSuite.getSkippedCount()));
                                                        case "Error" ->
                                                                fact.setValue(String.valueOf(testSuite.getErrorCount()));
                                                        case "Execution Time" ->
                                                                fact.setValue(String.valueOf(testSuite.getTotalTime()));
                                                    }
                                                }
                                        );
                                        case "action" -> body.getActions().forEach(
                                                action -> {
                                                    if (action.getId().equals("open-rp")) {
                                                        action.setUrl(rpLaunchUrl);
                                                    }
                                                }
                                        );
                                    }
                                }
                        )
                )
        );

        teamsWebhookService.notifyTeam(adaptiveCard);
    }
}
