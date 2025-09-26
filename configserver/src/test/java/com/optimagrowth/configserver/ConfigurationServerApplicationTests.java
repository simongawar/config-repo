package com.optimagrowth.configserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource; // Import this

@SpringBootTest
// Provide the minimal required property for the Config Server to load successfully
// In a typical setup, it needs a Git URI. We can set it to a dummy local path.
@TestPropertySource(properties = {
    "spring.cloud.config.server.git.uri=file:///tmp/config-repo" // A dummy local URI
})
class ConfigurationServerApplicationTests {

    @Test
    void contextLoads() {
        // This test now only confirms the context loads with the dummy Git URI provided above.
    }
}