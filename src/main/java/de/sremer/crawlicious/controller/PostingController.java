package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.PostingService;
import de.sremer.crawlicious.service.TagService;
import de.sremer.crawlicious.service.UserService;
import de.sremer.crawlicious.util.MyUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/postings")
public class PostingController {

    private PostingService postingService;
    private UserService userService;
    private TagService tagService;

    @Autowired
    PostingController(PostingService postingService, UserService userService, TagService tagService) {
        this.postingService = postingService;
        this.userService = userService;
        this.tagService = tagService;
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
    public ModelAndView insertPosting(
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "link", required = true) String link,
            @RequestParam(value = "tags", required = true) String tags) {

        Posting posting = new Posting(title, link, MyUtility.parseTags(tagService, tags));
        User currentUser = userService.getCurrentUser();
        posting.setUser(currentUser);
        postingService.insertPosting(posting);

        ModelAndView profile = new ModelAndView("redirect:/profile");
        profile.addObject("user", currentUser);
        return profile;
    }

    @PostMapping(value = "/update")
    public ModelAndView updatetPosting(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "link", required = true) String link,
            @RequestParam(value = "tags", required = true) String tags) {

        Posting posting = postingService.getPostingById(Long.valueOf(id));
        posting.setTitle(title);
        posting.setLink(link);
        posting.setTags(MyUtility.parseTags(tagService, tags));
        postingService.updatePosting(posting);

        return new ModelAndView("redirect:/profile");
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView updatePosting(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        User currentUser = userService.getCurrentUser();
        Posting posting = postingService.getPostingById(id);
        modelAndView.addObject("user", currentUser);
        modelAndView.addObject("id", id);
        modelAndView.addObject("title", posting.getTitle());
        modelAndView.addObject("link", posting.getLink());
        modelAndView.addObject("tags", MyUtility.getStringFromTags(posting.getTags()));
        modelAndView.setViewName("posting_update");
        return modelAndView;
    }

    @PostMapping(value = "/update/tag/add/")
    public ModelAndView addTag(
            @RequestParam(value = "postingId", required = true) String postingId,
            @RequestParam(value = "tag", required = true) String tag) {

        postingService.addTagToPosting(Long.valueOf(postingId), tag);
        return new ModelAndView("redirect:/profile");
    }

    @PostMapping(value = "/update/tag/remove/")
    public ModelAndView removeTag(
            @RequestParam(value = "postingId", required = true) String postingId,
            @RequestParam(value = "tagId", required = true) String tagId) {

        postingService.removeTagFromPosting(Long.valueOf(postingId), Long.valueOf(tagId));
        return new ModelAndView("redirect:/profile");
    }

    @GetMapping(value = "/csv_import")
    public ModelAndView csvImportGet() {
        System.out.println("foo");
        return new ModelAndView("csv_import");
    }

    @PostMapping(value = "/csv_import")
    public ModelAndView csvImportPost(@RequestParam(value = "csv", required = true) String csv) {

        String[] lines = csv.split("\n");
        for (String line : lines) {
            String[] values = line.split(",");

            Posting posting = new Posting(values[0], values[1], MyUtility.parseTags(tagService, values[2]));
            User currentUser = userService.getCurrentUser();
            posting.setUser(currentUser);
            postingService.insertPosting(posting);
        }

        ModelAndView profile = new ModelAndView("redirect:/profile");
        return profile;
    }

    @GetMapping(value = "/{id}")
    public Posting getPostingById(@PathVariable("id") int id) {
        return postingService.getPostingById(id);
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView deletePostingById(@PathVariable("id") int id) {

        postingService.deletePostingById(id);
        return new ModelAndView("redirect:/profile");
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
