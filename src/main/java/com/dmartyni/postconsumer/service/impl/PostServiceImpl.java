package com.dmartyni.postconsumer.service.impl;

import com.dmartyni.postconsumer.model.Post;
import com.dmartyni.postconsumer.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Value("${file.output.path}")
    private String outputFilePath;

    @Value("${json.placeholder.base.url}")
    private String jsonPlaceholderBaseURL;

    @Value("${json.placeholder.resource.url}")
    private String jsonPlaceholderResourceURL;

    private static final String JSON_EXTENSION = ".json";

    private RestTemplate restTemplate;

    private ObjectMapper mapper;

    @Autowired
    public PostServiceImpl(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    @Override
    public void writePosts() {
        List<Post> posts = retrievePosts();
        if(!posts.isEmpty()) {
            posts.forEach(p -> write(p, mapper));
        }
        log.info("All posts were successfully wrote to files");
    }

    private List<Post> retrievePosts() {
        log.info("Retrieving list of posts from external API: {}", jsonPlaceholderBaseURL);
        ResponseEntity<Post[]> response =
                restTemplate.getForEntity(jsonPlaceholderBaseURL + jsonPlaceholderResourceURL, Post[].class);

        return Optional.ofNullable(response.getBody()).map(Arrays::asList).orElse(Collections.emptyList());
    }

    private void write(Post post, ObjectMapper mapper) {
        try {
            mapper.writeValue(Paths.get(outputFilePath + post.getId()+ JSON_EXTENSION).toFile(), post);
        } catch (IOException e) {
            log.error("Exception during writing post {} to file", post.getId(), e);
        }
    }

}
