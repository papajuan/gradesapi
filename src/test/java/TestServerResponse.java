import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author aperminov
 * 21.02.2020
 */

@RunWith(SpringRunner.class)
@WebMvcTest
public class TestServerResponse {

    @LocalServerPort
    int localServerPort;

    @Autowired
    MockMvc mvc;

    final String disciplineId = "8aca61686cb9323e016cd0b1bdfa0188";
    final String json = "{\"uuid\":\"studen18hc2jg0000mamqshh334gitbc\",\"eduYear\":2019,\"semester\":\"autumn\",\"disciplines\":[{\"id\":\"8aca61686cb9323e016cd0b1bdfa0188\",\"title\":\"Оздоровительная физическая культура\",\"events\":[{\"type\":\"practice\",\"typeTitle\":\"практические занятия\",\"totalFactor\":1.0,\"testBeforeExam\":false,\"technologyCardAttestations\":[{\"type\":\"intermediate\",\"factor\":0.40,\"controls\":[{\"title\":\"зачет\",\"maxScore\":100,\"studentScore\":80.0}]},{\"type\":\"current\",\"factor\":0.60,\"controls\":[{\"title\":\"Контрольное мероприятие №1\",\"maxScore\":40,\"studentScore\":30.0},{\"title\":\"Контрольное мероприятие №2\",\"maxScore\":26,\"studentScore\":26.0},{\"title\":\"Посещаемость\",\"maxScore\":34,\"studentScore\":34.0}]}]}]},{\"id\":\"8aca61686c953f70016ca77ed589002c\",\"title\":\"Английский язык\",\"events\":[{\"type\":\"practice\",\"typeTitle\":\"практические занятия\",\"totalFactor\":1.0,\"testBeforeExam\":false,\"technologyCardAttestations\":[{\"type\":\"current\",\"factor\":0.6,\"controls\":[{\"title\":\"Домашняя работа\",\"maxScore\":10,\"studentScore\":10.0},{\"title\":\"Домашняя работа\",\"maxScore\":10,\"studentScore\":10.0},{\"title\":\"Домашняя работа\",\"maxScore\":10,\"studentScore\":10.0},{\"title\":\"Контрольная работа\",\"maxScore\":20,\"studentScore\":20.0},{\"title\":\"Контрольная работа\",\"maxScore\":20,\"studentScore\":20.0},{\"title\":\"Контрольная работа\",\"maxScore\":20,\"studentScore\":20.0},{\"title\":\"Посещение занятий\",\"maxScore\":10,\"studentScore\":10.0}]},{\"type\":\"intermediate\",\"factor\":0.4,\"controls\":[{\"title\":\"зачет\",\"maxScore\":100,\"studentScore\":100.0}]}]}]}]}";
    final String scheme = "http";
    final String host = "localhost";
    final String studentId = "studen18hc2jg0000mamqshh334gitbc";
    final String semester = "autumn";
    final int eduYear = 2019;

    @Test
    @WithMockUser(username = "123", roles = {"ADMIN"}, password = "123")
    public void testReturn200_technologyCards() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("studentId", studentId);
        params.put("eduYear", String.valueOf(eduYear));
        params.put("semester", semester);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameters(params);
        request.setMethod(HttpMethod.GET.toString());
        request.setServerPort(localServerPort);
        request.setServerName(host);
        request.setScheme(scheme);
        request.setRequestURI("/technologyCards");

        mvc.perform((RequestBuilder) request).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "123", roles = {"ADMIN"}, password = "123")
    public void testReturn200_scores() throws URISyntaxException, IOException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicHeader("disciplineId", studentId));

        HttpPost post = new HttpPost(getUri("scores", params));
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        HttpResponse response = getResponse(post);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusLine().getStatusCode());
    }

    private HttpResponse getResponse(HttpRequestBase requestBase) throws IOException {

        final HttpUriRequest request = requestBase;

//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, userPassword);
//        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        HttpClient client = HttpClientBuilder.create().build();

        return client.execute(request);
    }

    private URI getUri(String path, List<NameValuePair> params) throws URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(scheme).setHost(host).setPort(localServerPort).setPath(path)
                .setParameters(params);

        return builder.build();
    }
}
