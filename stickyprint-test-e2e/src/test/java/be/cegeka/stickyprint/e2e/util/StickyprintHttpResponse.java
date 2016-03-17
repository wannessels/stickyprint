package be.cegeka.stickyprint.e2e.util;

import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class StickyprintHttpResponse {


    public StickyprintHttpResponse(CloseableHttpResponse httpResponse) {
        Preconditions.checkArgument(httpResponse != null);
        this.httpResponse = httpResponse;
    }

    private CloseableHttpResponse httpResponse;


    public CloseableHttpResponse getRawHttpResponse() {
        return httpResponse;
    }

    public boolean isErrorResponse() {
        return httpStatus() != HttpStatus.SC_OK;
    }

    public String jsonAsString() throws IOException {
        return CharStreams.toString(new InputStreamReader(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8));
    }

    public int httpStatus() {
        return httpResponse.getStatusLine().getStatusCode();
    }

    public boolean isOkResponse() {
        return httpStatus() == HttpStatus.SC_OK;
    }
}
