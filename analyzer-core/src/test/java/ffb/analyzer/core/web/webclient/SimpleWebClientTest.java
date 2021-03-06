package ffb.analyzer.core.web.webclient;

import ffb.analyzer.core.web.request.EntityEnclosingRequestBuilder;
import ffb.analyzer.core.web.webclient.servlet.TestPerson;
import ffb.analyzer.core.web.webclient.servlet.TestServletInitializer;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Tests a {@link SimpleWebClient}. Assumes that port 9090 is available to run a Springboot Application.
 * See {@link TestServletInitializer} for more information about the service.
 */
public class SimpleWebClientTest {

    private static final String BASE_URL = "http://localhost:9000";
    private static final String GET_URL =  BASE_URL + "/test-get";
    private static final String POST_URL = BASE_URL + "/test-post";

    private static SimpleWebClient client;

    @BeforeClass
    public static void prepareTests() {
        TestServletInitializer.main(new String[1]);
        client = new SimpleWebClient();
    }

    @AfterClass
    public static void cleanupTests() {
        TestServletInitializer.closeContext();
    }

    /**
     * Tests a GET request.
     *
     * @throws IOException Thrown if request fails.
     */
    @Test
    public void testGetRequest() throws IOException {
        HttpGet request = new HttpGet(GET_URL);
        List<TestPerson> response = client.sendGet(request, TestPerson.class);

        Assert.assertNotNull(response);
        Assert.assertFalse(response.isEmpty());
        Assert.assertNotNull(response.get(0).getFirstName());
        Assert.assertNotNull(response.get(0).getLastName());
    }

    /**
     * Tests a POST request.
     *
     * @throws IOException Thrown if request fails.
     */
    @Test
    public void testPostRequest() throws IOException {
        HttpPost request = EntityEnclosingRequestBuilder.newInstance(HttpPost::new)
            .addUrl(POST_URL)
            .addRequestBodyParameter("firstName", "First")
            .addRequestBodyParameter("lastName", "Last")
            .build();

        client.sendPost(request);
    }

}
