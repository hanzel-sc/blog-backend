package com.example.blog.controller;

import com.example.blog.model.BlogPost;
import com.example.blog.repository.BlogPostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")  // Allow all origins; you can restrict this later if needed
public class BlogPostController {

    @Autowired
    private BlogPostRepository repo;

    @GetMapping
    public List<BlogPost> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public BlogPost create(@RequestBody BlogPost post) {
        return repo.save(post);
    }

    @PutMapping("/{id}")
    public BlogPost update(@PathVariable Long id, @RequestBody BlogPost p) {
        p.setId(id);
        return repo.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
