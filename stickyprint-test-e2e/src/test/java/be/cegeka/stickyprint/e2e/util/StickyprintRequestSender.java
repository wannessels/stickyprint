package be.cegeka.stickyprint.e2e.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StickyprintRequestSender {

    public static final int APP_PORT = 9000;
    private static final String ADMIN_STATUS_PAGE = "admin/status";

    @Autowired
    private CloseableHttpClient httpClient;

    @SneakyThrows
    public StickyprintHttpResponse requestAdminStatusPage() throws ClientProtocolException, IOException {
        CloseableHttpResponse httpResponse = httpClient.execute(httpGetRequestForAppRunningOnPort(ADMIN_STATUS_PAGE));
        return new StickyprintHttpResponse(httpResponse);
    }

    private HttpGet httpGetRequestForAppRunningOnPort(String requestPath) {
        String url = String.format("http://localhost:%d/%s", APP_PORT, requestPath);
        log.info("Requesting property on url:" + url);
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept-Encoding", "application/json;charset=utf-8");
        return httpGet;
    }

}
