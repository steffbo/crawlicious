package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.PostingService;
import de.sremer.crawlicious.service.TagService;
import de.sremer.crawlicious.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final UserService userService;
    private final PostingService postingService;
    private final TagService tagService;

    @GetMapping
    public ModelAndView profile(
            HttpServletRequest request,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        var modelAndView = new UserModelAndView(userService);

        User user = userService.getCurrentUser();
        if (user != null) {
            return profileById(request, user.getId(), page, size, null);
        } else {
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView profileById(HttpServletRequest request, @PathVariable(value = "id") long id,
                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                    @RequestParam(value = "tags", required = false) String tags) {
        var modelAndView = new UserModelAndView(userService);
        modelAndView.setViewName("profile");
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(page, size, sort);

        try {
            User user = userService.getOne(id);
            User ownUser = userService.getCurrentUser();
            if (user.getName().isEmpty()) {
                modelAndView.setViewName("redirect:/");
                return modelAndView;
            }
            modelAndView.addObject("user", user);
            boolean ownProfile = user.getEmail().equals(ownUser.getEmail());
            if (!ownProfile && user.isPrivateProfile()) {
                modelAndView.setViewName("redirect:/");
                return modelAndView;
            }
            modelAndView.addObject("ownProfile", ownProfile);

            Page<Posting> postings;
            String url = "/profile/" + user.getId();

            if (tags == null) {
                postings = postingService.getPostingsPageByUser(user, pageable);
            } else {
                List<Tag> tagsByName = tagService.getTagsByName(tags);
                postings = postingService.getPostingsPageByUserAndTags(user, tagsByName, pageable);
                url += "?tags=" + tags;
                modelAndView.addObject("tags", tagsByName.stream().map(Tag::getName).toArray());

                List<Tag> relatedTagsForTagByUserId = postingService.getRelatedTagsForTagByUserId(user, tagsByName);
                modelAndView.addObject("relatedTags", relatedTagsForTagByUserId);
            }
            PageWrapper<Posting> postingPageWrapper = new PageWrapper<>(postings, url);
            modelAndView.addObject("postings", postingPageWrapper.getContent());
            modelAndView.addObject("page", postingPageWrapper);

            List<Tag> tagsByUserId = tagService.getTagsByUserId(user.getId());
            modelAndView.addObject("allTags", tagsByUserId);

            modelAndView.addObject("request", request);
            return modelAndView;
        } catch (EntityNotFoundException exception) {
            log.error("Exception! {}", exception.getMessage());
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
    }

}

