package de.sremer.crawlicious.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @UuidGenerator
    private UUID id;

    private String role;

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof Role && id == ((Role) o).id;
    }
}
