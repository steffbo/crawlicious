package de.sremer.crawlicious.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long id;

    @Column(name = "role")
    private String role;

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof Role && id == ((Role) o).id;
    }
}
