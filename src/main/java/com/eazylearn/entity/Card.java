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
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@SuppressWarnings({"LombokDataInspection", "LombokEqualsAndHashCodeInspection", "com.haulmont.jpb.LombokEqualsAndHashCodeInspection", "com.haulmont.jpb.LombokDataInspection"})
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

    @Column(name = "is_favourite")
    private Boolean isFavourite = false;

    @NotNull
    @ToString.Exclude
    @Column(name = "user_id")
    private String userId;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "set_card",
            joinColumns = {@JoinColumn(name = "card_id")},
            inverseJoinColumns = {@JoinColumn(name = "set_id")}
    )
    private List<CardSet> linkedCardSets = new ArrayList<>();

    public boolean addLinkedCardSet(CardSet linkedCardSet) {
        if (!linkedCardSets.contains(linkedCardSet)) {
            return linkedCardSets.add(linkedCardSet);
        }
        return false;
    }

    public boolean removeLinkedCardSet(CardSet linkedCardSet){
        return linkedCardSets.remove(linkedCardSet);
    }
}
