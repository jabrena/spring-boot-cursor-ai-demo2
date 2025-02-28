package info.jab.ms.adapter.web;

import info.jab.ms.config.TestContainersJdbcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ActorControllerIntegrationTest extends TestContainersJdbcConfig {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnFirstTenActors() throws Exception {
        mockMvc.perform(get("/api/v1/actors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].firstName").exists())
                .andExpect(jsonPath("$[0].lastName").exists());
    }
    
    @Test
    public void shouldCreateNewActor() throws Exception {
        String requestBody = """
                {
                    "firstName": "Test",
                    "lastName": "Actor"
                }
                """;
                
        mockMvc.perform(post("/api/v1/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("Test")))
                .andExpect(jsonPath("$.lastName", is("Actor")))
                .andExpect(jsonPath("$.lastUpdate", notNullValue()));
    }
} 