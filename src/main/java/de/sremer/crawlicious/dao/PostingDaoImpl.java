package de.sremer.crawlicious.dao;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Qualifier("fakeDao")
public class PostingDaoImpl implements PostingDao {

    private static Map<Integer, Posting> postings;

    static {

        postings = new HashMap<Integer, Posting>() {
            {
                put(1, new Posting("foo", "foo.com", Arrays.asList(new Tag("foo"))));
                put(2, new Posting("hello", "hello.org", Arrays.asList(new Tag("hi"), new Tag("welcome"))));
                put(3, new Posting("world", "pla.net", Arrays.asList(new Tag("crawlicious"))));
            }
        };
    }

    @Override
    public Collection<Posting> getPostings() {
        return this.postings.values();
    }

    @Override
    public Posting getPostingById(int id) {
        return this.postings.get(id);
    }

    @Override
    public void deletePostingById(int id) {
        this.postings.remove(id);
    }

    @Override
    public void updatePosting(Posting posting) {
        int postingId = (int) posting.getId();
        Posting p = this.postings.get(postingId);
        p.setLink(posting.getLink());
        p.setTags(posting.getTags());
        p.setTitle(posting.getTitle());
        this.postings.put(postingId, posting);
    }

    @Override
    public void insertPosting(Posting posting) {
        this.postings.put((int) posting.getId(), posting);
    }
}
