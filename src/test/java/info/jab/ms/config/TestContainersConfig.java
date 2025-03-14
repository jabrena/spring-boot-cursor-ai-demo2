package info.jab.ms.config;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class TestContainersConfig implements QuarkusTestResourceLifecycleManager {

    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Override
    public Map<String, String> start() {
        POSTGRES.start();
        
        Map<String, String> properties = new HashMap<>();
        properties.put("quarkus.datasource.jdbc.url", POSTGRES.getJdbcUrl());
        properties.put("quarkus.datasource.username", POSTGRES.getUsername());
        properties.put("quarkus.datasource.password", POSTGRES.getPassword());
        
        return properties;
    }

    @Override
    public void stop() {
        if (POSTGRES != null && POSTGRES.isRunning()) {
            POSTGRES.stop();
        }
    }
} 