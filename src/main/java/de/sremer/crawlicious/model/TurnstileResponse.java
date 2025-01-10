package de.sremer.crawlicious.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnstileResponse {
  private boolean success;
  private String challenge_ts;
  private String hostname;
  private List<String> errorCodes;
}