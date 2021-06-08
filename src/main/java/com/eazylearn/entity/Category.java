package com.eazylearn.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
@Table(name = "category")
public class Category extends BaseEntity { // todo: add validation
    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private Long userId;
}
