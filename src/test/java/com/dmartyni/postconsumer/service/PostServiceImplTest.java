package com.dmartyni.postconsumer.service;

import com.dmartyni.postconsumer.model.Post;
import com.dmartyni.postconsumer.service.impl.PostServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    @Mock
    private ObjectMapper mapper;
    @Mock
    private RestTemplate restTemplate;

    PostService postService;

    @Before
    public void setup() {
        postService = new PostServiceImpl(restTemplate, mapper);
    }

    @Test
    public void shouldWriteToFile() throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity responseEntity = new ResponseEntity(preparePosts(), header, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(responseEntity);
        postService.writePosts();

        verify(mapper, times(2)).writeValue(any(File.class), any(Post.class));
    }

    @Test
    public void shouldNotWriteToFile() throws IOException {
        ResponseEntity emptyResponseEntity = new ResponseEntity(HttpStatus.LOCKED);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(emptyResponseEntity);
        postService.writePosts();

        verify(mapper, never()).writeValue(any(File.class), any(Post.class));
    }

    private Post[] preparePosts() {
        Post[] posts = new Post[2];
        Post postOne = new Post(1,2,"Title One", "Body post one");
        Post postTwo = new Post(1,1,"Title Two", "Body post two");
        posts[0] = postOne;
        posts[1] = postTwo;
        return posts;
    }
}
