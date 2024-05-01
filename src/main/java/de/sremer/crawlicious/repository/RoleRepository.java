package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findRoleByRole(String role);
}