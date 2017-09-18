package de.sremer.crawlicious.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "posting")
public class Posting {

    @Id
    @GeneratedValue
    @Column(name = "posting_id")
    private long id;

    private String title;

    private String link;

    @Column(name = "user_id")
    private long userId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "posting_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    public Posting() {
    }

    public Posting(String title, String link, Set<Tag> tags) {
        this.title = title;
        this.link = link;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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

}
