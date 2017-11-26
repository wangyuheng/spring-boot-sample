package wang.crick.study.globalexception.api;

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
import wang.crick.study.globalexception.directory.ErrorCode;

import static org.junit.Assert.assertTrue;

/**
 * Created by crick on 2017/11/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApiTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserApi userApi;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private JSONObject getUserApiResult(int id) throws Exception {
        String path = "/user/" + id;
        return new JSONObject(mockMvc.perform(MockMvcRequestBuilders.get(path))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString());
    }

    @Test
    public void test_exception_handler() throws Exception {
        int i = 1;
        assertTrue(ErrorCode.UserIdError.getCode() == getUserApiResult(i).getInt("code"));
        i++;
        assertTrue(ErrorCode.Error.getCode() == getUserApiResult(i).getInt("code"));
    }


}