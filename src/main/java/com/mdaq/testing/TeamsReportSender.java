package com.mdaq.testing;

import com.mdaq.testing.gitlab.GitlabService;
import com.mdaq.testing.teams.TeamsNotificationCardHandler;
import com.mdaq.testing.teams.TeamsWebhookService;

import java.io.IOException;
import java.nio.file.Files;

public class TeamsReportSender {
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
        TeamsNotificationCardHandler teamsNotificationCardHandler = new TeamsNotificationCardHandler();


        var latestTestReport = service.getLatestTestReport(service.getUrl());
        var adaptiveCard = teamsNotificationCardHandler.loadCardTemplate();
        teamsNotificationCardHandler.updateCardInfo(latestTestReport, adaptiveCard, rpLaunchUrl);
        teamsWebhookService.notifyTeam(adaptiveCard);
    }


}
