package com.globaljobsnepal.blog.service;

import com.globaljobsnepal.blog.entity.MyBlog;
import com.globaljobsnepal.blog.repo.MyBlogRepository;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 2/25/2026 4:46 PM
 * -------------------------------------------------------------
 */

@Service
public class MyBlogService {

    private final MyBlogRepository myBlogRepository;

    public MyBlogService(MyBlogRepository myBlogRepository) {
        this.myBlogRepository = myBlogRepository;
    }

    public Object save(MyBlog myBlog) {
        return myBlogRepository.save(myBlog);
    }

    public Optional<MyBlog> findByTitle(String title) {
        return myBlogRepository.findByTitle(title);
    }

    public List<MyBlog> findAll() {
        return myBlogRepository.findAll();
    }
}
