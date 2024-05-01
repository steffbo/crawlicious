package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordRestTokenRepository extends JpaRepository<PasswordResetToken, UUID> {

    PasswordResetToken findByToken(String token);
}
