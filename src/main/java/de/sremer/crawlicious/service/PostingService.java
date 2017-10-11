package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostingService {

    @Autowired
    private PostingRepository postingRepository;

    public List<Posting> getPostings() {
        return this.postingRepository.findAll();
    }

    public Posting getPostingById(long id) {
        return this.postingRepository.findOne(id);
    }

    public void deletePostingById(long id) {
        this.postingRepository.delete(id);
    }

    public void updatePosting(Posting posting) {
        this.postingRepository.save(posting);
    }

    public void insertPosting(Posting posting) {

        posting.setDate(System.currentTimeMillis());
        this.postingRepository.save(posting);
    }
}
