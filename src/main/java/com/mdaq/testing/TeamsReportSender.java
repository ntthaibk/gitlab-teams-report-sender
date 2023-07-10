package com.mdaq.testing;

import com.mdaq.testing.reportportal.ReportPortalLaunchInfo;
import com.mdaq.testing.reportportal.ReportPortalService;
import com.mdaq.testing.teams.AdaptiveCard;
import com.mdaq.testing.teams.TeamsNotificationCardHandler;
import com.mdaq.testing.teams.TeamsWebhookService;

import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;

public class TeamsReportSender {
    public static void main(String[] args) throws IOException, InterruptedException {
         ReportPortalLaunchInfo rpLaunchInfo;
        if (args[0] == null || args[0].trim().isEmpty()) {
            System.out.println("Please provide a valid path to ReportPortal Launch URL");
            return;
        } else {
            rpLaunchInfo = GsonHandler.getGson().fromJson(new FileReader(args[0].trim()), ReportPortalLaunchInfo.class);
        }
        if (rpLaunchInfo == null) {
            throw new IllegalStateException("ReportPortalLaunchInfo is null");
        }

        var rpLaunchUrl = String.format("%s/ui/#%s/launches/all/%s", rpLaunchInfo.getBaseUrl(), rpLaunchInfo.getProjectName(), rpLaunchInfo.getLaunchUuid());

        ReportPortalService reportPortalService = new ReportPortalService();
        TeamsWebhookService teamsWebhookService = new TeamsWebhookService();
        TeamsNotificationCardHandler teamsNotificationCardHandler = new TeamsNotificationCardHandler();

        var latestTestReport = reportPortalService.getLaunchByUuid(reportPortalService.getUrl(rpLaunchInfo));
        var gitLabStartTime = Instant.parse(System.getenv("CI_PIPELINE_CREATED_AT"));
        var launchStartTime = Instant.ofEpochMilli(latestTestReport.getStartTime());

        AdaptiveCard adaptiveCard;
        System.out.println("GitLab pipeline start time: " + gitLabStartTime.toEpochMilli());
        System.out.println("ReportPortal launch start time: " + launchStartTime.toEpochMilli());
        if(launchStartTime.isBefore(gitLabStartTime)){
            System.out.println("Launch start time is before GitLab pipeline start time. Probably a compilation error, please re-check");
            adaptiveCard = teamsNotificationCardHandler.loadErrorCard();
            teamsNotificationCardHandler.updateErrorCardInfo(adaptiveCard);
        }else{
            adaptiveCard = teamsNotificationCardHandler.loadCardTemplate();
            teamsNotificationCardHandler.updateCardInfo(latestTestReport, adaptiveCard, rpLaunchUrl);
        }

        teamsWebhookService.notifyTeam(adaptiveCard);
    }


}
