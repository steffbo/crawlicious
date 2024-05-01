package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.ContainersConfig;
import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(ContainersConfig.class)
public class PostingRepositoryTest {

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        postingRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void save() {
        // given
        postingRepository.save(createPosting());

        // when
        List<Posting> postings = postingRepository.findAll();

        // then
        assertThat(postings).hasSize(1);
    }

    private Posting createPosting() {
        User user = new User();
        user.setEmail("user@mail.com");
        user.setName("user");
        user.setRegisteredOn(System.currentTimeMillis());
        user.setPassword("foo");
        User savedUser = userRepository.save(user);

        Posting posting = new Posting();
        posting.setTitle("title");
        posting.setLink("link");
        posting.setUser(savedUser);
        return posting;
    }

}