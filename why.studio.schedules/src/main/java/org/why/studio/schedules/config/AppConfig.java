package org.why.studio.schedules.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;

@Configuration
@Import({
        RestTemplate.class
})
public class AppConfig {

    @Value("${output-datetime-format}")
    private String dateTimePattern;

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern(dateTimePattern);
    }

}
