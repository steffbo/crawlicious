package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.repository.PostingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostingService {

    private PostingRepository postingRepository;
    private TagService tagService;

    public PostingService(PostingRepository postingRepository, TagService tagService) {
        this.postingRepository = postingRepository;
        this.tagService = tagService;
    }

    public List<Posting> getPostings() {
        return this.postingRepository.findAll();
    }

    public List<Posting> getPostingsByUser(User user) {
        return this.postingRepository.findAllByUser(user);
    }

    public Page<Posting> getPostingsPageByUser(User user, Pageable pageable) {
        return this.postingRepository.findByUser(user, pageable);
    }

    public Page<Posting> getPostingsPageByUserAndTags(User user, List<Tag> tags, Pageable pageable) {

        List<String> tagList = tags.stream().map(Tag::getName).collect(Collectors.toList());
        List<Posting> byUserAndTag = this.postingRepository.findByUserAndTags(user.getId(), tagList);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int count = byUserAndTag.size();

        int from = pageNumber * pageSize;
        int to = from + pageSize;
        to = to > count ? count : to;
        List<Posting> subList = byUserAndTag.subList(from, to);

        return new PageImpl<>(subList, pageable, count);
    }

    public Posting getPostingById(long id) {
        return this.postingRepository.findOne(id);
    }

    public void deletePostingById(long id) {
        this.postingRepository.delete(id);
    }

    public void updatePosting(Posting posting) {
        this.postingRepository.save(posting);
    }

    public void insertPosting(Posting posting) {

        posting.setDate(System.currentTimeMillis());
        this.postingRepository.save(posting);
    }

    public void addTagToPosting(long postingId, String tag) {
        Posting posting = postingRepository.getOne(postingId);
        posting.addTag(tagService.getTagByName(tag));
    }

    public void removeTagFromPosting(Long postingId, Long tagId) {
        Posting posting = postingRepository.getOne(postingId);
        Tag tag = tagService.getTag(tagId);
        posting.removeTag(tag);
    }
}
