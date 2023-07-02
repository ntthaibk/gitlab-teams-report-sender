import com.mdaq.testing.gitlab.GitlabService;
import com.mdaq.testing.teams.TeamsNotificationCardHandler;
import com.mdaq.testing.teams.TeamsWebhookService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UnitTest {
    @Test
    void when_emptyTestResult(){

        var rpLaunchUrl = "yoyo";
        GitlabService service = new GitlabService();
        TeamsNotificationCardHandler teamsNotificationCardHandler = new TeamsNotificationCardHandler();

        var latestTestReport = service.getEmptyReport();
        var adaptiveCard = teamsNotificationCardHandler.loadCardTemplate();
        Assert.expectThrows(IllegalStateException.class, () -> {
            teamsNotificationCardHandler.updateCardInfo(latestTestReport, adaptiveCard, rpLaunchUrl);
        });
    }

}
