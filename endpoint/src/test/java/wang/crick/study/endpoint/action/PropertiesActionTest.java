package wang.crick.study.endpoint.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertiesActionTest {

    private TestRestTemplate restTemplate;

    @Value("${management.port}")
    private String managementPort;

    @Before
    public void setupMockMvc() {
        restTemplate = new TestRestTemplate();
    }

    @Test
    public void test_properties_action() throws Exception {
        String path = "http://localhost:" + managementPort + "/custom/properties";
        Map<String, String> result = restTemplate.getForObject(path, HashMap.class);
        assertEquals(result.get("management.port"), managementPort);
    }

    @Test
    public void test_help() throws Exception {
        String path = "http://localhost:" + managementPort + "/custom/help";
        Set<String> result = restTemplate.getForObject(path, Set.class);
        assertTrue(result.contains("custom/properties"));
    }

    @Test
    public void test_rand() throws Exception {
        String path = "http://localhost:" + managementPort + "/custom/" + new Random().nextInt();
        String result = restTemplate.getForObject(path, String.class);
        assertEquals("try /help for action list", result);

    }

}