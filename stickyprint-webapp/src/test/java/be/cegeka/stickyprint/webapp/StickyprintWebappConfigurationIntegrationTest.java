package be.cegeka.stickyprint.webapp;

import be.cegeka.stickyprint.webapp.admin.AdminController;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {StickyprintApplication.class})
@WebAppConfiguration
public class StickyprintWebappConfigurationIntegrationTest {


    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private AdminController adminController;

    @Test
    public void contextLoads() {
        Assertions.assertThat(adminController).isNotNull();
    }


}
