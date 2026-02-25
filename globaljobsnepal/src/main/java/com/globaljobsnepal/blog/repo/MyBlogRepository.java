package com.globaljobsnepal.blog.repo;

import com.globaljobsnepal.blog.entity.MyBlog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 2/25/2026 4:45 PM
 * -------------------------------------------------------------
 */
public interface MyBlogRepository extends JpaRepository<MyBlog, Long> {
    Optional<MyBlog> findByTitle(String title);
}
