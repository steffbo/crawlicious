package de.sremer.crawlicious.service.external;

import de.sremer.crawlicious.model.TurnstileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TurnstileService {

  @Value("${turnstile.secret}")
  private String turnstileSecret;

  private final RestTemplate restTemplate;

  public TurnstileService() {
    this.restTemplate = new RestTemplate();
  }

  public boolean verify(String cfResponse, String remoteIp) {
    // If there is no token, treat as failure
    if (cfResponse == null || cfResponse.trim().isEmpty()) {
      return false;
    }

    String verifyUrl = "https://challenges.cloudflare.com/turnstile/v0/siteverify";

    // Prepare request parameters for the POST request
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("secret", turnstileSecret);
    params.add("response", cfResponse);
    params.add("remoteip", remoteIp);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
    try {
      // Send POST request to Cloudflare
      ResponseEntity<TurnstileResponse> response =
          restTemplate.postForEntity(verifyUrl, requestEntity, TurnstileResponse.class);

      // Check if Cloudflare says success == true
      TurnstileResponse turnstileResponse = response.getBody();
      return turnstileResponse != null && turnstileResponse.isSuccess();
    } catch (Exception e) {
      log.error("Error while verifying turnstile response", e);
      return false;
    }
  }
}
