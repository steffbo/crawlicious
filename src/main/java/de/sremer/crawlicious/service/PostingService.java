package de.sremer.crawlicious.service;

import de.sremer.crawlicious.dao.PostingDao;
import de.sremer.crawlicious.model.Posting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PostingService {

    @Autowired
    @Qualifier("fakeDao")
    private PostingDao postingDao;

    public Collection<Posting> getPostings() {
        return postingDao.getPostings();
    }

    public Posting getPostingById(int id) {
        return this.postingDao.getPostingById(id);
    }

    public void deletePostingById(int id) {
        this.postingDao.deletePostingById(id);
    }

    public void updatePosting(Posting posting) {
        this.postingDao.updatePosting(posting);
    }

    public void insertPosting(Posting posting) {
        postingDao.insertPosting(posting);
    }
}
