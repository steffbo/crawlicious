package de.sremer.crawlicious.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private String title;

    @Getter
    @Setter
    private String link;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private long date;

    @Getter
    @Setter
    private boolean secret;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
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

    public Posting(String title, String link, Set<Tag> tags, boolean secret) {
        this(title, link, tags);
        this.secret = secret;
    }

    public Set<Tag> getTags() {
        return new TreeSet(tags);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getPosts().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getPosts().remove(this);
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
