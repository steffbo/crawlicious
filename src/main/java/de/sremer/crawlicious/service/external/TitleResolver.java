package de.sremer.crawlicious.service.external;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TitleResolver {

    private String loadRemotePage(String url) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .followRedirects(true)
                .followSslRedirects(true)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();
        Call call = okHttpClient.newCall(request);

        try (final Response response = call.execute()) {
            if (response.code() != 200) {
                return null;
            }
            return response.body().string();
        } catch (IOException e) {
            return null;
        }
    }

    public String getWebsiteTitle(String url) {
        String remotePage = loadRemotePage(url);
        if (remotePage != null) {
            String title = remotePage.substring(remotePage.indexOf("<title>") + 7, remotePage.indexOf("</title>"));
            return title.trim();
        }
        return "";
    }
}
