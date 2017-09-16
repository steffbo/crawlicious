package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/postings")
public class PostingController {

    @Autowired
    private PostingService postingService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Posting> getPostings() {
        return postingService.getPostings();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Posting getPostingById(@PathVariable("id") int id) {
        return postingService.getPostingById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePostingById(@PathVariable("id") int id) {
        postingService.deletePostingById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updatePosting(@RequestBody Posting posting) {
        postingService.updatePosting(posting);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertPosting(@RequestBody Posting posting) {
        postingService.insertPosting(posting);
    }
}
