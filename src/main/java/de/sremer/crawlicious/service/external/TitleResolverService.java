package de.sremer.crawlicious.service.external;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

@Service
public class TitleResolverService {

    public String getWebsiteTitle(String url) {
        InputStream response = null;
        try {
            response = new URL(url).openStream();
            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            String title = responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>"));
            return title;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }
}
