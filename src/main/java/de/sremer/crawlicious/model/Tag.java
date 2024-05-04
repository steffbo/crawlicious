package de.sremer.crawlicious.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tag implements Comparable<Tag> {

    @Id
    @UuidGenerator
    private UUID id;

    @NaturalId
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof Tag && Objects.equals(name, ((Tag) o).name);
    }

    @Override
    public int compareTo(Tag o) {
        return name.compareTo(o.name);
    }
}
