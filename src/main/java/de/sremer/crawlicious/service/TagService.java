package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostingService postingService;

    public Tag getTag(long id) {
        return this.tagRepository.findById(id).get();
    }

    public Tag getTagByName(String tagName) {
        Tag tag = tagRepository.findTagByName(tagName);
        return tag != null ? tag : new Tag(tagName);
    }

    public List<Tag> getTagsByName(String tags) {
        return tagRepository.findByNameIn(tags.split(" "));
    }

    public List<Tag> getTagsByUserId(long userId) {
        return tagRepository.findEverythingForUserId(userId);
    }

    public List<String> getTagNamesByUserId(long userId) {
        return tagRepository.findAllTagNamesForUserId(userId);
    }

    public List<Tag> getRelatedTagsForTagByUserId(User user, List<Tag> tags) {

        ArrayList<Tag> relatedTags = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 500);
        Page<Posting> postingsPageByUserAndTags = postingService.getPostingsPageByUserAndTags(user, tags, pageable);
        postingsPageByUserAndTags.getContent().forEach(p -> relatedTags.addAll(p.getTags()));
        return relatedTags;
    }

}
