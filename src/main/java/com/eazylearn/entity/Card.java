package com.eazylearn.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.LAZY;

@SuppressWarnings({"LombokDataInspection", "LombokEqualsAndHashCodeInspection"})
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
@Table(name = "card")
public class Card extends BaseEntity {

    @Length(min = 1, max = 100)
    @Column(name = "term")
    private String term;

    @Length(min = 1, max = 1000)
    @Column(name = "definition")
    private String definition;

    @NotNull
    @Column(name = "proficiency_level")
    private Double proficiencyLevel;

    @Column(name = "created_time")
    private final Long createdTime = Calendar.getInstance().getTimeInMillis();

    @Column(name = "is_favourite")
    private Boolean isFavourite;

    @NotNull
    @ToString.Exclude
    @Column(name = "user_id")
    private UUID userId;

    @ManyToMany(cascade = {
            PERSIST,
            MERGE,
            REFRESH,
            DETACH},
            fetch = LAZY)
    @JoinTable(
            name = "set_card",
            joinColumns = {@JoinColumn(name = "card_id")},
            inverseJoinColumns = {@JoinColumn(name = "set_id")}
    )
    private List<CardSet> linkedCardSets = new ArrayList<>();

}
