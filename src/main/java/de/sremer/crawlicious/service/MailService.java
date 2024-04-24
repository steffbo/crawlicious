package de.sremer.crawlicious.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class MailService {

    @Value("${mailgun.url}")
    private String url;

    @Value("${mailgun.apikey}")
    private String apikey;

    @Async
    public void send(String to, String subject, String text) {

        final String uri = url + "/messages";

        HttpHeaders headers = new HttpHeaders();
        String basicAuthorization = "Basic " + new String(Base64.getEncoder().encode(("api:" + apikey).getBytes()));
        headers.set("Authorization", basicAuthorization);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("from", "making@woofl.es");
        map.add("to", to);
        map.add("subject", subject);
        map.add("text", text);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> stringResponseEntity = template.postForEntity(uri, request, String.class);
    }
}
