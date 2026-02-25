package com.globaljobsnepal.blog.entity;

import com.globaljobsnepal.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 2/25/2026 4:44 PM
 * -------------------------------------------------------------
 */

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MyBlog extends BaseEntity<Long> {

    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String content;
}
