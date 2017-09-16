package de.sremer.crawlicious.dao;

import de.sremer.crawlicious.model.Posting;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@Qualifier("Db")
public class DbPostingDao implements PostingDao {

    @Override
    public Collection<Posting> getPostings() {
        return null;
    }

    @Override
    public Posting getPostingById(int id) {
        return null;
    }

    @Override
    public void deletePostingById(int id) {

    }

    @Override
    public void updatePosting(Posting posting) {

    }

    @Override
    public void insertPosting(Posting posting) {

    }
}
