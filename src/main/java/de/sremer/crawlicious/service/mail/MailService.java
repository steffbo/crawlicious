package de.sremer.crawlicious.service.mail;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void send(String to, String subject, String text);
}
