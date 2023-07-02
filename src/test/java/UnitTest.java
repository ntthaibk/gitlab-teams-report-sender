import com.mdaq.testing.FileHandler;
import com.mdaq.testing.reportportal.ReportPortalLaunchInfo;
import com.mdaq.testing.reportportal.ReportPortalService;
import org.testng.annotations.Test;

import java.io.IOException;

public class UnitTest {

    @Test
    void rp_service_test() throws IOException, InterruptedException {
        ReportPortalService service = new ReportPortalService();
        ReportPortalLaunchInfo launchInfo = FileHandler.jsonFileToObject("reportportal-launch-id.txt", ReportPortalLaunchInfo.class);
        service.getLaunchByUuid(service.getUrl(launchInfo));
    }

}
