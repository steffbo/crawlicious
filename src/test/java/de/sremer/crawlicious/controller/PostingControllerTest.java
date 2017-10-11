package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.util.MyUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PostingControllerTest {

    @Test
    public void testParseTags() {

        String userInputTags = "foo bar, asdahd";
        Set<Tag> tags = MyUtility.parseTags(userInputTags);

        System.out.println(tags);

        Assert.assertTrue(tags.size() == 3);

    }
}
