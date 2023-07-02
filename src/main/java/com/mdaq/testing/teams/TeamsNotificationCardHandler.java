package com.mdaq.testing.teams;

import com.mdaq.testing.GsonHandler;
import com.mdaq.testing.reportportal.GetLaunchByUuidResponse;

import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TeamsNotificationCardHandler {

    public AdaptiveCard loadCardTemplate() {
        var card = getClass().getClassLoader().getResourceAsStream("card.json");
        if (card == null) {
            throw new IllegalStateException("Failed to load card.json");
        }
        return GsonHandler.getGson().fromJson(new InputStreamReader(card), AdaptiveCard.class);
    }

    public void updateCardInfo(GetLaunchByUuidResponse launchInfo, AdaptiveCard adaptiveCard, String rpLaunchUrl) {
        adaptiveCard.getAttachments().forEach(
                attachment -> attachment.getContent().getBody().forEach(
                        body -> {
                            switch (body.getId()) {
                                case "card-title" -> {
                                    body.getItems().forEach(
                                            item -> {
                                                if (item.getId().equals("general-info")) {
                                                    item.getFacts().forEach(
                                                            fact -> {
                                                                switch (fact.getTitle()) {
                                                                    case "Date" -> {
                                                                        LocalDateTime time = Instant.ofEpochMilli(launchInfo.getStartTime())
                                                                                .atZone(ZoneId.systemDefault()).toLocalDateTime();
                                                                        fact.setValue(time.toString());
                                                                    }
                                                                    case "Environment" ->
                                                                            fact.setValue(System.getenv("ENV"));
                                                                    case "Test Suite" ->
                                                                            fact.setValue(launchInfo.getName() + " - " + launchInfo.getNumber());
                                                                    case "Test Result" ->
                                                                            fact.setValue(launchInfo.getStatus());
                                                                }
                                                            }
                                                    );
                                                }
                                            }
                                    );
                                    switch (launchInfo.getStatus()){
                                        case "PASSED" -> body.setStyle("good");
                                        case "FAILED" -> body.setStyle("attention");
                                    }
                                }
                                case "test-result" -> body.getFacts().forEach(
                                        fact -> {
                                            switch (fact.getTitle()) {
                                                case "Total" ->
                                                        fact.setValue(String.valueOf(launchInfo.getStatistics().getExecutions().getTotal()));
                                                case "Passed" ->
                                                        fact.setValue(String.valueOf(launchInfo.getStatistics().getExecutions().getPassed()));
                                                case "Failed" ->
                                                        fact.setValue(String.valueOf(launchInfo.getStatistics().getExecutions().getFailed()));
                                                case "Skipped" ->
                                                        fact.setValue(String.valueOf(launchInfo.getStatistics().getExecutions().getSkipped()));
                                                case "Execution Time" ->
                                                        fact.setValue(String.valueOf(launchInfo.getApproximateDuration()));
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
        );
    }


}
