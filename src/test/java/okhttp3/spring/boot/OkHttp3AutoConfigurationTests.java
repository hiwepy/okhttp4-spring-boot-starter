package okhttp3.spring.boot;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.codahale.metrics.MetricRegistry;

import net.minidev.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.spring.boot.actuate.OkHttp3Endpoint;

/**
 * okhttp3 client auto configuration tests
 * @author linux_china
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OkHttp3AutoConfigurationTests {
    private static ApplicationContext context;

    @BeforeClass
    public static void setUp() {
        context = new AnnotationConfigApplicationContext(
                OkHttp3AutoConfigurationTests.class, OkHttp3AutoConfiguration.class);
    }

    @Bean
    public MetricRegistry registry() {
        return new MetricRegistry();
    }
    
    @Test
    public void testHttpClient() throws Exception {
        OkHttpClient client = context.getBean(OkHttpClient.class);
        String url = "http://ip.mvnsearch.org";
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        OkHttp3Endpoint endpoint = context.getBean(OkHttp3Endpoint.class);
        System.out.println(JSONObject.toJSONString(endpoint.getMetrics()));
    }

}