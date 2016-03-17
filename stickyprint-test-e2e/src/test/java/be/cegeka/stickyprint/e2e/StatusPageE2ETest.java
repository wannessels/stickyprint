package be.cegeka.stickyprint.e2e;

import be.cegeka.stickyprint.e2e.util.StickyprintHttpResponse;
import be.cegeka.stickyprint.e2e.util.StickyprintHttpResponseAssert;
import be.cegeka.stickyprint.e2e.util.StickyprintRequestSender;
import be.cegeka.stickyprint.e2e.util.config.E2EUtilConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {E2EUtilConfig.class})
public class StatusPageE2ETest {

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private StickyprintRequestSender stickyprintRequestSender;

    private static ConfigurableApplicationContext configurableApplicationContext;

    @BeforeClass
    public static void setUp() {
        configurableApplicationContext = new StickyprintMainRunner().bootStickyprintApplication();
    }

    @Test
    public void givenAStickyprintAppThatIsRunning_whenRequestingTheStatusPage_thenStatuscode200Returned() {
        StickyprintHttpResponse stickyprintHttpResponse = stickyprintRequestSender.requestAdminStatusPage();
        StickyprintHttpResponseAssert.assertThat(stickyprintHttpResponse).hasHttpStatusCodeOk();
    }

    @AfterClass
    public static void tearDown() {
        configurableApplicationContext.close();
        configurableApplicationContext = null;
    }
}
