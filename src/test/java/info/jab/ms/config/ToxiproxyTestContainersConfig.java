package info.jab.ms.config;

import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.ToxiproxyContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;

import java.io.IOException;

@Testcontainers
public abstract class ToxiproxyTestContainersConfig {

    private static final Network network = Network.newNetwork();
    
    @Container
    private static final ToxiproxyContainer toxiproxy = new ToxiproxyContainer("shopify/toxiproxy:2.1.0")
            .withNetwork(network);
    
    @Container
    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("schema.sql")
            .withNetwork(network)
            .withNetworkAliases("postgres");
    
    protected static Proxy dbProxy;
    protected static ToxiproxyClient toxiproxyClient;
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        try {
            toxiproxyClient = new ToxiproxyClient(toxiproxy.getHost(), toxiproxy.getMappedPort(8474));
            dbProxy = toxiproxyClient.createProxy("postgres", "0.0.0.0:8666", "postgres:5432");
            
            // Configure database URL to use Toxiproxy
            registry.add("spring.datasource.url", 
                () -> String.format("jdbc:postgresql://%s:%d/testdb", 
                    toxiproxy.getHost(), toxiproxy.getMappedPort(8666)));
            registry.add("spring.datasource.username", postgres::getUsername);
            registry.add("spring.datasource.password", postgres::getPassword);
            registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to configure Toxiproxy", e);
        }
    }
    
    /**
     * Simulate database downtime by adding a timeout toxic to the proxy
     * @throws IOException if the toxic cannot be added
     */
    protected void simulateDatabaseDown() throws IOException {
        dbProxy.toxics().timeout("database_timeout", ToxicDirection.DOWNSTREAM, 10000);
    }
    
    /**
     * Restore database connection by removing all toxics
     * @throws IOException if the toxics cannot be removed
     */
    protected void restoreDatabaseConnection() throws IOException {
        dbProxy.toxics().getAll().forEach(toxic -> {
            try {
                toxic.remove();
            } catch (IOException e) {
                throw new RuntimeException("Failed to remove toxic", e);
            }
        });
    }
} 