package be.cegeka.stickyprint.webapp.property.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@PropertySources({
        @PropertySource(value = "classpath:application.properties"),
        @PropertySource(value = "classpath:app.info"),
})
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PropertyReadingConfig {
}
