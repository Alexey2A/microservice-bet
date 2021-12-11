package com.example.parserfootball.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties
@PropertySource(value = "classpath:bookmaker.yml", factory = YamlPropertySourceFactory.class)
public class ParserConfig {
    private Map<String, Map<String, List<String>>> bookmakers;

    public void setBookmakers(Map<String, Map<String, List<String>>> bookmakers) {
        this.bookmakers = bookmakers;
    }

    public Map<String, Map<String, List<String>>> getBookmakers() {
        return bookmakers;
    }
}
