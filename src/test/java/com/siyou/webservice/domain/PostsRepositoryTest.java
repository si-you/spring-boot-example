package com.siyou.webservice.domain;

import com.siyou.webservice.domain.posts.Posts;
import com.siyou.webservice.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void loadSavedPost() {
        postsRepository.save(Posts.builder()
                .title("Test article")
                .content("Test paragraph")
                .author("seungil.you@gmail.com")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle(), is("Test article"));
        assertThat(posts.getContent(), is("Test paragraph"));
    }

    @Test
    public void registerBaseTimeEntity() {
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                .title("Test article")
                .content("Test paragraph")
                .author("seungil.you@gmail.com")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertTrue(posts.getCreatedDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));
    }

    @Test
    @Transactional(readOnly = true)
    public void testFindPosts() {
        postsRepository.save(Posts.builder()
                .title("test1")
                .content("test!!")
                .author("tester")
                .build());

        postsRepository.save(Posts.builder()
                .title("test2")
                .content("test!!")
                .author("tester")
                .build());

        List<Posts> postsList = postsRepository.findAllDesc().collect(Collectors.toList());

        assertThat(postsList.get(0).getTitle(), is("test2"));
        assertThat(postsList.get(1).getTitle(), is("test1"));
    }
}
