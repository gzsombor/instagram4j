/**
 * 
 */
package org.brunocvcunha.instagram4j.requests;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.brunocvcunha.instagram4j.InstagramConstants;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
 * @author zsombor
 *
 */
@AllArgsConstructor
@Log4j
public class InstagramDownloadRequest extends InstagramRequest<Path> {

    private Path outputFile;
    private String url;

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public Path execute() throws ClientProtocolException, IOException {
        HttpGet get = new HttpGet(url);
        get.addHeader("Connection", "close");
        get.addHeader("Accept", "*/*");
        get.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        get.addHeader("Cookie2", "$Version=1");
        get.addHeader("Accept-Language", "en-US");
        get.addHeader("User-Agent", InstagramConstants.USER_AGENT);

        HttpResponse response = api.getClient().execute(get);
        api.setLastResponse(response);

        int resultCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            log.info("Result for accessing '" + url + "' code=" + resultCode + ", entity=" + entity.getContentType());
            try (OutputStream out = Files.newOutputStream(outputFile)) {
                entity.writeTo(out);
                out.flush();
            }
        } else {
            log.info("Result for accessing '" + url + "' code=" + resultCode);
        }

        get.releaseConnection();

        return outputFile;
    }

    public final Path parseResult(int resultCode, String content) {
        throw new IllegalArgumentException("This shouldn't be called");
    }

    @Override
    public String getUrl() {
        return url;
    }

}
