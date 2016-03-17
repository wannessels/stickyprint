package be.cegeka.stickyprint.webapp;


import be.cegeka.stickyprint.core.api.CoreConfig;
import be.cegeka.stickyprint.port.rest.config.PortRestConfig;
import be.cegeka.stickyprint.webapp.admin.config.AdminConfig;
import be.cegeka.stickyprint.webapp.property.config.PropertyReadingConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// No @SpringBootApplication was used, since it forced a component scan on the current package, which is not desirable
@Configuration
@EnableAutoConfiguration(exclude = {ActiveMQAutoConfiguration.class, LiquibaseAutoConfiguration.class})
@Import({
        PropertyReadingConfig.class,
        AdminConfig.class,
        PortRestConfig.class,
        CoreConfig.class
})
public class StickyprintApplication {
}
