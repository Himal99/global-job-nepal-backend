package com.globaljobsnepal.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.globaljobsnepal.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name="Token_generation_log")
public class TokenGenerationLog extends BaseEntity<Long> {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate = new Date();
    @ManyToOne
    private User user;
    private Long tokenId;

    public TokenGenerationLog(User loggedInUser, Long id) {
        this.user = loggedInUser;
        this.tokenId = id;
    }
}
