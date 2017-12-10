package de.sremer.crawlicious.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "posting")
public class Posting implements Comparable<Posting> {

    @Id
    @GeneratedValue
    @Column(name = "posting_id")
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @NotNull(message = "Title is required")
    private String title;

    @Getter
    @Setter
    @NotNull(message = "Link is required")
    @URL(message = "Provided URL is invalid")
    private String link;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private long date;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "posting_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))

    @Setter
    private Set<Tag> tags;

    public Posting() {
    }

    public Posting(String title, String link, Set<Tag> tags) {
        this.title = title;
        this.link = link;
        this.tags = tags;
    }

    public Set<Tag> getTags() {
        TreeSet treeSet = new TreeSet(tags);
        return treeSet;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getPosts().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getPosts().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof Posting && id == ((Posting) o).id;
    }

    @Override
    public int compareTo(Posting other) {
        return Long.compare(other.date, this.date);
    }
}
