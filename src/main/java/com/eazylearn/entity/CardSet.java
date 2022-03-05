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

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@SuppressWarnings({"com.haulmont.jpb.LombokDataInspection", "com.haulmont.jpb.LombokEqualsAndHashCodeInspection"})
@Entity
@Table(name = "cardset")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class CardSet extends BaseEntity { // todo: add validation

    @Column(name = "name")
    private String name;

    @Column(name = "is_favourite")
    private Boolean isFavourite = false;

    @Column(name = "user_id")
    private String userId;

    @ManyToMany(mappedBy = "linkedCardSets",
            cascade = {
                    PERSIST,
                    MERGE,
                    REFRESH,
                    DETACH})
    private List<Card> linkedCards = new ArrayList<>();
}
