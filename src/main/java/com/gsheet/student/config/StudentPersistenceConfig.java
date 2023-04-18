package com.gsheet.student.config;

import com.gsheet.student.service.GoogleSheetsRangeProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.student.persistence")
@Getter
@Setter
public class StudentPersistenceConfig {

    private String rangeFrom;

    private String rangeTo;

    @Bean
    public GoogleSheetsRangeProvider studentPersistenceRangeProvider() {
        return new GoogleSheetsRangeProvider(rangeFrom, rangeTo);
    }
}
