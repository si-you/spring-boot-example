package com.siyou.webservice.service;

import com.siyou.webservice.domain.posts.Posts;
import com.siyou.webservice.domain.posts.PostsRepository;
import com.siyou.webservice.dto.PostsMainResponseDto;
import com.siyou.webservice.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsServiceTest {

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void saveDtoToDb() {
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .author("jojoldu@gmail.com")
                .content("테스트")
                .title("테스트 타이틀")
                .build();

        postsService.save(dto);

        //then
        Posts posts = postsRepository.findAll().get(0);
        assertThat(posts.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(posts.getContent()).isEqualTo(dto.getContent());
        assertThat(posts.getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    public void getPostList() {
        // given
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .author("tester")
                .content("test")
                .title("test title")
                .build();
        postsService.save(dto);

        // when
        PostsMainResponseDto posts = postsService.findAllDesc().get(0);

        assertThat(posts.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(posts.getTitle()).isEqualTo(dto.getTitle());
    }
}