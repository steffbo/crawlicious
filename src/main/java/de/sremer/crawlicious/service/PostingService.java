package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.repository.PostingRepository;
import de.sremer.crawlicious.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostingService {

    private PostingRepository postingRepository;
    private UserRepository userRepository;
    private TagService tagService;

    @Autowired
    public void setPostingRepository(PostingRepository postingRepository) {
        this.postingRepository = postingRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setTagService(TagService tagService) {
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
        List<String> tagList = tags.stream()
                .map(Tag::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        List<Posting> byUserAndTag = this.postingRepository.findByUserAndTags(user.getId(), tagList);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int count = byUserAndTag.size();

        int from = pageNumber * pageSize;
        int to = from + pageSize;
        to = Math.min(to, count);
        List<Posting> subList = byUserAndTag.subList(from, to);

        return new PageImpl<>(subList, pageable, count);
    }

    public Posting getPostingById(long id) {
        return this.postingRepository.findById(id).get();
    }

    public void deletePostingById(long id) {
        this.postingRepository.deleteById(id);
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

    public boolean linkAlreadyExists(Long userId, String url) {
        User user = userRepository.findUserById(userId);
        List<Posting> postings = postingRepository.findAllByUser(user);
        return postings.stream().anyMatch(p -> p.getLink().contains(url) || url.contains(p.getLink()));
    }

    public void deleteAllPostingsByUser(User user) {
        List<Posting> postingsByUser = getPostingsByUser(user);
        postingRepository.deleteAll(postingsByUser);
    }
}
