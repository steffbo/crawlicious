package de.sremer.crawlicious.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "posting")
public class Posting {

    @Id
    @GeneratedValue
    @Column(name = "posting_id")
    private long id;

    private String title;

    private String link;

    @OneToMany
    private List<Tag> tags;

    public Posting() {
    }

    public Posting(String title, String link, List<Tag> tags) {
        this.title = title;
        this.link = link;
        this.tags = tags;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public long getId() {
        return id;
    }
}
