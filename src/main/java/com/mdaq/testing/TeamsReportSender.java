package com.mdaq.testing;

import com.mdaq.testing.reportportal.ReportPortalLaunchInfo;
import com.mdaq.testing.reportportal.ReportPortalService;
import com.mdaq.testing.teams.TeamsNotificationCardHandler;
import com.mdaq.testing.teams.TeamsWebhookService;

import java.io.FileReader;
import java.io.IOException;

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
        var adaptiveCard = teamsNotificationCardHandler.loadCardTemplate();
        teamsNotificationCardHandler.updateCardInfo(latestTestReport, adaptiveCard, rpLaunchUrl);
        teamsWebhookService.notifyTeam(adaptiveCard);
    }


}
