package com.globaljobsnepal.blog.controller;

import com.globaljobsnepal.blog.entity.MyBlog;
import com.globaljobsnepal.blog.service.MyBlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 2/25/2026 4:47 PM
 * -------------------------------------------------------------
 */

@RestController
@RequestMapping("/api/v1/my-blog")
public class MyBlogController {
    private final MyBlogService myBlogService;

    public MyBlogController(MyBlogService myBlogService) {
        this.myBlogService = myBlogService;
    }

    @PostMapping
    public ResponseEntity<?> saveMyBlog(@RequestBody MyBlog myBlog) {
        return ResponseEntity.ok(myBlogService.save(myBlog));
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(myBlogService.findAll());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok(myBlogService.findByTitle(title));
    }
}
