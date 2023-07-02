package com.mdaq.testing.teams;

import com.mdaq.testing.GsonHandler;
import com.mdaq.testing.gitlab.GetTestReportResponse;

import java.io.InputStreamReader;
import java.time.Instant;

public class TeamsNotificationCardHandler {

    public AdaptiveCard loadCardTemplate(){
        var card = getClass().getClassLoader().getResourceAsStream("card.json");
        if (card == null) {
            throw new IllegalStateException("Failed to load card.json");
        }
        return GsonHandler.getGson().fromJson(new InputStreamReader(card), AdaptiveCard.class);
    }

    public void updateCardInfo(GetTestReportResponse latestTestReport, AdaptiveCard adaptiveCard, String rpLaunchUrl) {
        Instant time = Instant.now();
        if(latestTestReport.getTestSuites().isEmpty()){
            throw new IllegalStateException("No test suites found");
        }
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
    }


}
