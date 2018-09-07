package wang.crick.study.i18n.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class I18nApiTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void should_return_message_by_different_accept_language() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept-language", "en");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> resultEn = restTemplate.exchange("http://localhost:"+port+"/hello", HttpMethod.GET, entity, String.class);
        assertEquals("hello", resultEn.getBody());

        headers.remove("accept-language");
        headers.add("accept-language", "zh");
        entity = new HttpEntity(headers);
        ResponseEntity<String> resultCh = restTemplate.exchange("http://localhost:"+port+"/hello", HttpMethod.GET, entity, String.class);
        assertEquals("你好", resultCh.getBody());
    }

    @Test
    public void should_return_zh_message_by_accept_language_zh_locale(){
        LocaleContextHolder.setLocale(Locale.CHINA);
        assertEquals("你好", ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale()).getString("10000"));
    }

    @Test
    public void should_return_zh_message_by_different_lang(){
        String lang = "en";
        ResponseEntity<String> resultEn = restTemplate.getForEntity("http://localhost:"+port+"/hello?lang="+lang, String.class);
        assertEquals("hello", resultEn.getBody());
        lang = "zh";
        ResponseEntity<String> resultCh = restTemplate.getForEntity("http://localhost:"+port+"/hello?lang="+lang, String.class);
        assertEquals("你好", resultCh.getBody());
    }

    @Test
    public void should_return_by_lang_when_set_lang_and_accept_language(){
        String lang = "zh";
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept-language", "en");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> resultEn = restTemplate.exchange("http://localhost:"+port+"/hello?lang="+lang, HttpMethod.GET, entity, String.class);
        assertEquals("你好", resultEn.getBody());
    }

}