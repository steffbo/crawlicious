package de.sremer.crawlicious.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private long id;

    @NaturalId
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Posting> posts = new ArrayList<>();

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Posting> getPosts() {
        return posts;
    }

    public void setPosts(List<Posting> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof Tag && Objects.equals(name, ((Tag) o).name);
    }
}
