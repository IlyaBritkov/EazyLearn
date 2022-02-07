package com.eazylearn.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"com.haulmont.jpb.LombokDataInspection", "com.haulmont.jpb.LombokEqualsAndHashCodeInspection"})
@Entity
@Table(name = "cardSet")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class CardSet extends BaseEntity { // todo: add validation

    @Column(name = "name")
    private String name;

    @Column(name = "is_favourite")
    private Boolean isFavourite = false;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToMany(mappedBy = "linkedCardSets")
    private List<Card> linkedCards = new ArrayList<>();

}
