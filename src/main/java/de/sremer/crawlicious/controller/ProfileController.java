package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.PostingService;
import de.sremer.crawlicious.service.TagService;
import de.sremer.crawlicious.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
public class ProfileController {

    static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    private PostingService postingService;

    private TagService tagService;

    @Autowired
    public ProfileController(UserService userService, PostingService postingService, TagService tagService) {

        this.userService = userService;
        this.postingService = postingService;
        this.tagService = tagService;
    }

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public ModelAndView profile(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        User user = userService.getCurrentUser();
        if (user != null) {
            return profileById(user.getId(), page, size, "");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @RequestMapping(value = {"/profile/{id}"}, method = RequestMethod.GET)
    public ModelAndView profileById(@PathVariable(value = "id") long id,
                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                    @RequestParam(value = "tags", required = false) String tags) {
        ModelAndView modelAndView = new ModelAndView("profile");
        Pageable pageable = new PageRequest(page, size);

        try {
            User user = userService.getOne(id);
            User ownUser = userService.getCurrentUser();
            if (user.getName().isEmpty()) {
                return new ModelAndView("redirect:/");
            }
            modelAndView.addObject("user", user);
            modelAndView.addObject("ownProfile", (user == ownUser));

            Page<Posting> postings;

            if (tags == null) {
                postings = postingService.getPostingsPageByUser(user, pageable);
            } else {
                List<Tag> tagsByName = tagService.getTagsByName(tags);
                postings = postingService.getPostingsPageByUserAndTags(user, tagsByName, pageable);
            }
            PageWrapper<Posting> postingPageWrapper = new PageWrapper<>(postings, "/profile/" + user.getId());
            modelAndView.addObject("postings", postingPageWrapper.getContent());
            modelAndView.addObject("page", postingPageWrapper);
            return modelAndView;
        } catch (EntityNotFoundException exception) {
            LOG.warn("Exception! " + exception.getMessage());
            return new ModelAndView("redirect:/");
        }
    }

}
