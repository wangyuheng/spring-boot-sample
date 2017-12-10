package wang.crick.study.validator.api;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidatorApiTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private JSONObject requestApi(String path) throws Exception {
        return new JSONObject(mockMvc.perform(MockMvcRequestBuilders.get(path))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString());
    }

    @Test
    public void test_user_param_valid() throws Exception {
        String rightPath = "/user/" + "12?id=123" + "&password=123456";
        assertTrue(10000 == requestApi(rightPath).getInt("code"));
        String errorPath = "/user/" + "abc";
        assertTrue(10001 == requestApi(errorPath).getInt("code"));
    }


    @Test
    public void test_simpleuser_param_valid() throws Exception {
        String rightPath = "/simpleuser/" + "12?id=123" + "&password=123456";
        assertTrue(10000 == requestApi(rightPath).getInt("code"));
        String errorPath = "/simpleuser/" + "abc";
        assertTrue(10001 == requestApi(errorPath).getInt("code"));
    }

}