package de.sremer.crawlicious.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.UuidGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Posting implements Comparable<Posting> {

    @Id
    @UuidGenerator
    private UUID id;

    private String title;

    private String link;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private long date;

    private boolean secret;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "posting_tag",
            joinColumns = @JoinColumn(name = "posting_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Getter(AccessLevel.NONE)
    @BatchSize(size = 500)
    private Set<Tag> tags;

    public Posting(String title, String link, Set<Tag> tags) {
        this.title = title;
        this.link = link;
        this.tags = tags;
    }

    public Posting(String title, String link, Set<Tag> tags, boolean secret) {
        this(title, link, tags);
        this.secret = secret;
    }

    public Set<Tag> getTags() {
        return new TreeSet<>(tags);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
//        tag.getPosts().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
//        tag.getPosts().remove(this);
    }

    public String getShortenedLink() {
        int linkLimit = 80;
        return link.length() < linkLimit ? link : link.substring(0, linkLimit) + "...";
    }

    public String getPostingDate(String dateformat) {
        return new SimpleDateFormat(dateformat).format(new Date(this.date));
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
