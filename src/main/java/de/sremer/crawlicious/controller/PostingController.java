package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.PostingService;
import de.sremer.crawlicious.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@RestController
@RequestMapping("/postings")
public class PostingController {

    private PostingService postingService;
    private UserService userService;

    @Autowired
    PostingController(PostingService postingService, UserService userService) {
        this.postingService = postingService;
        this.userService = userService;
    }

    @GetMapping(value = "/insert")
    public ModelAndView insertPosting() {
        ModelAndView modelAndView = new ModelAndView();
        User currentUser = userService.getCurrentUser();
        modelAndView.addObject("user", currentUser);
        modelAndView.setViewName("posting_insert");
        return modelAndView;
    }

    @PostMapping(value = "/insert")
    public void insertPosting(@ModelAttribute Posting posting) {
        postingService.insertPosting(posting);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView updatePosting(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        User currentUser = userService.getCurrentUser();
        modelAndView.addObject("user", currentUser);
        modelAndView.addObject("posting", postingService.getPostingById(id));
        modelAndView.setViewName("posting");
        return modelAndView;
    }

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
    public void insertJsonPosting(@RequestBody Posting posting) {
        postingService.insertPosting(posting);
    }


}
