package de.sremer.crawlicious.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
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

    @ManyToMany(mappedBy = "tags")
    private List<Posting> posts = new ArrayList<>();

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
