package com.globaljobsnepal.auth.entity;


import com.globaljobsnepal.auth.enums.Roles;
import com.globaljobsnepal.core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Himal Rai on 1/14/2024
 * Sb Solutions Nepal pvt.ltd
 * Project sb-back-core.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Role extends BaseEntity<Long> {

    @Enumerated(EnumType.STRING)
    private Roles name;


}
