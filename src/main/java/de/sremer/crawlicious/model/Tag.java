package de.sremer.crawlicious.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tag")
public class Tag implements Comparable<Tag> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private long id;

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
