package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.repository.PostingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostingServiceTest {

    private PostingService postingService;

    @MockBean
    private PostingRepository postingRepository;

    @MockBean
    private TagService tagService;

    @MockBean
    private User user;

    @MockBean
    private Page<Posting> postings;

    @Before
    public void setup() {
        postingService = new PostingService(postingRepository, tagService);
    }

    @Test
    public void testGetPostings() {

//        int page = 0;
//        int size = 20;
//        when(postingRepository.findAllByUser(user, new PageRequest(page, size))).thenReturn(postings);
//
//        Iterable<Posting> retrievedPostings = postingService.getPostings(user, page, size);
//
//        retrievedPostings.forEach(a -> System.out.println(a.getTitle()));
//
//        assertThat(retrievedPostings, is(equalTo(postings)));

    }


}