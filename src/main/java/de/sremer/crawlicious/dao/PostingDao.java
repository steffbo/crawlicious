package de.sremer.crawlicious.dao;

import de.sremer.crawlicious.model.Posting;

import java.util.Collection;

public interface PostingDao {
    Collection<Posting> getPostings();

    Posting getPostingById(int id);

    void deletePostingById(int id);

    void updatePosting(Posting posting);

    void insertPosting(Posting posting);
}
