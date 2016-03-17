package be.cegeka.stickyprint.e2e;


import be.cegeka.stickyprint.webapp.StickyprintApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class StickyprintMainRunner {

    public static void main(String[] args) throws Exception {
        new StickyprintMainRunner().bootStickyprintApplication();
    }

    public ConfigurableApplicationContext bootStickyprintApplication() {
        return new SpringApplicationBuilder().sources(StickyprintApplication.class).run("--spring.api.location=classpath:/application-local.properties");
    }
}
