package de.sremer.crawlicious.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tag")
public class Tag implements Comparable<Tag> {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    @Getter
    @Setter
    private long id;

    @NaturalId
    @Getter
    @Setter
    private String name;

    @ManyToMany(mappedBy = "tags")
    @Getter
    @Setter
    private List<Posting> posts = new ArrayList<>();

    public Tag() {
    }

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
