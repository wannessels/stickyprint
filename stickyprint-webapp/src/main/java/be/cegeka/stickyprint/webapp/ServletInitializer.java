package be.cegeka.stickyprint.webapp;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

public class ServletInitializer extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources( StickyprintApplication.class);
    }


}
