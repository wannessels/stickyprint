package be.cegeka.stickyprint.e2e.util.config;

import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("be.cegeka.stickyprint.e2e.util")
public class E2EUtilConfig {


    @Bean
    public CloseableHttpClient httpClient() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultConnectionConfig(ConnectionConfig.DEFAULT);
        return builder.build();
    }

}
