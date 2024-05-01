package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.PostingService;
import de.sremer.crawlicious.service.TagService;
import de.sremer.crawlicious.service.UserService;
import de.sremer.crawlicious.util.MyUtility;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/postings")
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;
    private final UserService userService;
    private final TagService tagService;

    @GetMapping(value = "/insert")
    public ModelAndView insertPosting() {
        var modelAndView = new UserModelAndView(userService);
        User currentUser = userService.getCurrentUser();
        List<String> tags = tagService.getTagNamesByUserId(currentUser.getId());
        modelAndView.addObject("user", currentUser);
        modelAndView.addObject("tags", tags);
        modelAndView.setViewName("posting_insert");
        return modelAndView;
    }

    @GetMapping(value = "/exists")
    public boolean linkAlreadyExists(@RequestParam(value = "id") UUID id,
                                     @RequestParam(value = "url") String url) {
        System.out.println("id = " + id);
        System.out.println("url = " + url);
        return postingService.linkAlreadyExists(id, url);
    }

    @PostMapping(value = "/insert")
    public ModelAndView insertPosting(
            @Validated @RequestParam(value = "title") String title,
            @Validated @RequestParam(value = "link") String link,
            @RequestParam(value = "tags") String tags,
            @RequestParam(value = "secret") String secret) {

        UrlValidator urlValidator = new UrlValidator();

        if (urlValidator.isValid(link)) {
            boolean isSecret = secret.equals("true");
            Posting posting = new Posting(title, link, MyUtility.parseTags(tagService, tags), isSecret);
            User currentUser = userService.getCurrentUser();
            posting.setUser(currentUser);
            postingService.insertPosting(posting);

            var profile = new UserModelAndView(userService, "redirect:/profile");
            profile.addObject("user", currentUser);
            return profile;
        } else {
            var insert = new UserModelAndView(userService, "posting_insert");
            User currentUser = userService.getCurrentUser();
            List<String> tagNames = tagService.getTagNamesByUserId(currentUser.getId());
            insert.addObject("user", currentUser);
            insert.addObject("tags", tagNames);
            insert.addObject("linkError", "Provided link is invalid.");
            insert.addObject("oldLink", link);
            return insert;
        }
    }

    @PostMapping(value = "/update")
    public ModelAndView updatetPosting(
            @RequestParam(value = "id") UUID id,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "link") String link,
            @RequestParam(value = "tags") String tags,
            @RequestParam(value = "secret") String secret) {

        Posting posting = postingService.getPostingById(id);
        posting.setTitle(title);
        posting.setLink(link);
        posting.setTags(MyUtility.parseTags(tagService, tags));
        posting.setSecret(secret.equals("true"));
        postingService.updatePosting(posting);

        return new UserModelAndView(userService, "redirect:/profile");
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView updatePosting(@PathVariable("id") UUID id) {
        var modelAndView = new UserModelAndView(userService);
        User currentUser = userService.getCurrentUser();
        Posting posting = postingService.getPostingById(id);
        List<String> tags = tagService.getTagNamesByUserId(currentUser.getId());
        modelAndView.addObject("user", currentUser);
        modelAndView.addObject("id", id);
        modelAndView.addObject("title", posting.getTitle());
        modelAndView.addObject("link", posting.getLink());
        modelAndView.addObject("tags", tags);
        modelAndView.addObject("postingTags", posting.getTags().stream().map(Tag::getName).toArray());
        modelAndView.addObject("secret", posting.isSecret());
        modelAndView.setViewName("posting_update");
        return modelAndView;
    }

    @PostMapping(value = "/update/tag/add/")
    public ModelAndView addTag(
            @RequestParam(value = "postingId") UUID postingId,
            @RequestParam(value = "tag") String tag) {

        postingService.addTagToPosting(postingId, tag);
        return new UserModelAndView(userService, "redirect:/profile");
    }

    @PostMapping(value = "/update/tag/remove/")
    public ModelAndView removeTag(
            @RequestParam(value = "postingId") UUID postingId,
            @RequestParam(value = "tagId") UUID tagId) {

        postingService.removeTagFromPosting(postingId, tagId);
        return new UserModelAndView(userService, "redirect:/profile");
    }

    @GetMapping(value = "/csv_import")
    public ModelAndView csvImportGet() {
        return new UserModelAndView(userService, "csv_import");
    }

    @PostMapping(value = "/csv_import")
    public ModelAndView csvImportPost(@RequestParam(value = "csv") String csv) {

        String[] lines = csv.split("\n");
        for (String line : lines) {
            String[] values = line.split(",");

            // TODO: validate input

            Posting posting = new Posting(values[0], values[1], MyUtility.parseTags(tagService, values[2]));
            User currentUser = userService.getCurrentUser();
            posting.setUser(currentUser);
            postingService.insertPosting(posting);
        }

        return new UserModelAndView(userService, "redirect:/profile");
    }

    @GetMapping(value = "/{id}")
    public Posting getPostingById(@PathVariable("id") UUID id) {
        return postingService.getPostingById(id);
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView deletePostingById(@PathVariable("id") UUID id) {
        postingService.deletePostingById(id);
        return new UserModelAndView(userService, "redirect:/profile");
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
