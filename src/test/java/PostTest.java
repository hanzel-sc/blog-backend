package com.example.blog.controller;

import com.example.blog.model.BlogPost;
import com.example.blog.repository.BlogPostRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BlogPostController.class)
public class PostTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogPostRepository repo;

    private BlogPost post;

    @BeforeEach
    public void setUp() {
        post = new BlogPost();
        post.setId(1L);
        post.setTitle("My First Post");
        post.setContent("This is a simple post");
        post.setCreatedAt(LocalDate.now());
    }

    @Test
    public void testGetAllPosts() throws Exception {
        when(repo.findAll()).thenReturn(Arrays.asList(post));

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("My First Post"))
                .andExpect(jsonPath("$[0].content").value("This is a simple post"))
                .andExpect(jsonPath("$[0].createdAt").value(post.getCreatedAt().toString()));
    }

    @Test
    public void testCreatePost() throws Exception {
        when(repo.save(any(BlogPost.class))).thenReturn(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content("{\"title\":\"My First Post\",\"content\":\"This is a simple post\",\"createdAt\":\"" + post.getCreatedAt() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("My First Post"))
                .andExpect(jsonPath("$.content").value("This is a simple post"))
                .andExpect(jsonPath("$.createdAt").value(post.getCreatedAt().toString()));
    }
}