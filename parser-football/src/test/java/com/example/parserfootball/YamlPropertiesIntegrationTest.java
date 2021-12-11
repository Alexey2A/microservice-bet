package com.example.parserfootball;

import com.example.parserfootball.config.ParserConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class YamlPropertiesIntegrationTest {

    @Autowired
    private ParserConfig parserConfig;

    @Test
    public void whenFactoryProvidedThenYamlPropertiesInjected() {
        System.out.println(parserConfig.getBookmakers());
    }
}