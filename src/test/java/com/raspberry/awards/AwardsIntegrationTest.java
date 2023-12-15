package com.raspberry.awards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AwardsIntegrationTest {

    @Autowired
    private ApplicationContext ctx;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void sendGetRequestToAwardsEndpoint_shouldCalculateResultAndReturnExpectedJsonResponse() throws Exception {
        String jsonFileName = "expected_response.json";

        String expectedJsonResponse = ctx.getResource("classpath:" + jsonFileName).getContentAsString(StandardCharsets.UTF_8);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/awards",
                String.class)).isEqualTo(expectedJsonResponse);
    }
}
