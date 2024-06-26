package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.repository.PostingRepository;
import de.sremer.crawlicious.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostingService {

    private final PostingRepository postingRepository;
    private final UserRepository userRepository;
    private final TagService tagService;

    public List<Posting> getPostingsByUser(User user) {
        return postingRepository.findAllByUser(user);
    }

    public Page<Posting> getPostingsPageByUser(User user, Pageable pageable) {
        return postingRepository.findByUser(user, pageable);
    }

    public Page<Posting> getPostingsPageByUserAndTags(User user, List<Tag> tags, Pageable pageable) {
        List<String> tagList = tags.stream()
                .map(Tag::getName)
                .map(String::toLowerCase)
                .toList();

        return postingRepository.findByUserAndTags(user.getId(), tagList, pageable);
    }

    public Posting getPostingById(UUID id) {
        return postingRepository.findById(id).get();
    }

    public void deletePostingById(UUID id) {
        postingRepository.deleteById(id);
    }

    public void updatePosting(Posting posting) {
        postingRepository.save(posting);
    }

    public void insertPosting(Posting posting) {
        posting.setDate(System.currentTimeMillis());
        postingRepository.save(posting);
    }

    public void addTagToPosting(UUID postingId, String tag) {
        Posting posting = postingRepository.getOne(postingId);
        posting.addTag(tagService.getTagByName(tag));
    }

    public void removeTagFromPosting(UUID postingId, UUID tagId) {
        Posting posting = postingRepository.getOne(postingId);
        Tag tag = tagService.getTag(tagId);
        posting.removeTag(tag);
    }

    public boolean linkAlreadyExists(UUID userId, String url) {
        User user = userRepository.findUserById(userId);
        List<Posting> postings = postingRepository.findAllByUser(user);
        return postings.stream().anyMatch(p -> p.getLink().contains(url) || url.contains(p.getLink()));
    }

    public void deleteAllPostingsByUser(User user) {
        List<Posting> postingsByUser = getPostingsByUser(user);
        postingRepository.deleteAll(postingsByUser);
    }

    public List<Tag> getRelatedTagsForTagByUserId(User user, List<Tag> tags) {
        ArrayList<Tag> relatedTags = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 500);
        Page<Posting> postingsPageByUserAndTags = getPostingsPageByUserAndTags(user, tags, pageable);
        postingsPageByUserAndTags.getContent().forEach(p -> relatedTags.addAll(p.getTags()));
        return relatedTags;
    }
}
