package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.repository.TagRepository;
import org.springframework.stereotype.Service;

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

}
