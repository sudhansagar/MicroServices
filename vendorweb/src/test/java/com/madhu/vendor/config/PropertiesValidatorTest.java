package com.madhu.vendor.config;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class PropertiesValidatorTest {

    @Test
    @DisplayName("context fails when properties file missing")
    void contextFailsWhenPropertiesFileMissing() {
        ApplicationContextRunner runner = new ApplicationContextRunner()
                .withUserConfiguration(PropertiesValidator.class)
                .withPropertyValues("app.config.file=classpath:/no-such-file.properties");

        assertThrows(IllegalStateException.class, () ->
                runner.run(context -> {}));
    }
}
