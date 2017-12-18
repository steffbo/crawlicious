package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag getTag(long id) {
        return this.tagRepository.findOne(id);
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

    public List<Tag> getRelatedTagsForTagByUserId(long userId, List<String> tags) {
        return tagRepository.findPossibleForUserIdAndSelectedTag(userId, tags);
    }

}
