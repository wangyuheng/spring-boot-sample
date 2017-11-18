package wang.crick.study.httplog.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApiTest {

    private MockMvc mockMvc;

    @Autowired
    private UserApi userApi;

    @Autowired
    private WebApplicationContext context;

    private ByteArrayOutputStream outContent;
    private int userId = new Random().nextInt(10);
    private int age = new Random().nextInt(10);
    private long username = new Random().nextLong();
    private long password = new Random().nextLong();

    @Before
    public void setup() {
        // 坚挺控制台输出
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //项目拦截器有效
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        //单个类，拦截器无效
        // mockMvc = MockMvcBuilders.standaloneSteup(userApi).build();
    }


    @Test
    public void test_log() throws Exception {
        String path = "/user/log/" + userId;
        String uri = path + "?age=" + age;
        RequestBuilder request = MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
        String console = outContent.toString();
        assertTrue(console.contains("REQUEST_LOG"));
        assertFalse(console.contains("HEADER_PARAMS"));
        assertTrue(console.contains("RESPONSE_LOG"));
        assertTrue(console.contains(path));
        assertTrue(console.contains(String.valueOf(age)));
        assertFalse(console.contains(String.valueOf(username)));
        assertFalse(console.contains(String.valueOf(password)));
    }

    @Test
    public void test_log_header() throws Exception {
        String path = "/user/log/pwd/" + userId;
        String uri = path + "?age=" + age;
        RequestBuilder request = MockMvcRequestBuilders.get(uri)
                .header("username", username)
                .header("password", password)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());

        String console = outContent.toString();
        assertTrue(console.contains("REQUEST_LOG"));
        assertTrue(console.contains("HEADER_PARAMS"));
        assertTrue(console.contains("RESPONSE_LOG"));
        assertTrue(console.contains(path));
        assertTrue(console.contains(String.valueOf(age)));
        assertFalse(console.contains(String.valueOf(username)));
        assertTrue(console.contains(String.valueOf(password)));
    }

    @Test
    public void test_log_header_excludeResponse() throws Exception {
        String path = "/user/log/pwdExcludeResponse/" + userId;
        String uri = path + "?age=" + age;
        RequestBuilder request = MockMvcRequestBuilders.get(uri)
                .header("username", username)
                .header("password", password)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());

        String console = outContent.toString();
        assertTrue(console.contains("REQUEST_LOG"));
        assertTrue(console.contains("HEADER_PARAMS"));
        assertFalse(console.contains("RESPONSE_LOG"));
        assertTrue(console.contains(path));
        assertTrue(console.contains(String.valueOf(age)));
        assertTrue(console.contains(String.valueOf(username)));
        assertFalse(console.contains(String.valueOf(password)));
    }

}