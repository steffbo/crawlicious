package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag getTag(UUID id) {
        return this.tagRepository.findById(id).get();
    }

    public Tag getTagByName(String tagName) {
        Tag tag = tagRepository.findTagByName(tagName);
        return tag != null ? tag : new Tag(tagName);
    }

    public List<Tag> getTagsByName(String tags) {
        return tagRepository.findByNameIn(tags.split(" "));
    }

    public List<Tag> getTagsByUserId(UUID userId) {
        return tagRepository.findEverythingForUserId(userId);
    }

    public List<String> getTagNamesByUserId(UUID userId) {
        return tagRepository.findAllTagNamesForUserId(userId);
    }
}
