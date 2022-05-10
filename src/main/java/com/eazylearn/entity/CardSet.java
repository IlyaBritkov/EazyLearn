package com.eazylearn.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @NotNull
    @Column(name = "proficiency_level")
    private Double proficiencyLevel;

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

    // todo: fix: move checking if contains to the caller
    public boolean addLinkedCard(Card linkedCard) {
        if (!linkedCards.contains(linkedCard)) {
            linkedCard.addLinkedCardSet(this);
            return linkedCards.add(linkedCard);
        }
        return false;
    }

    public boolean addLinkedCard(List<Card> cards) {
        return linkedCards.addAll(cards);
    }

    public boolean removeLinkedCard(Card linkedCard) {
        linkedCard.removeLinkedCardSet(this);
        return linkedCards.remove(linkedCard);
    }

    public void removeAllLinkedCards() {
        linkedCards.forEach(card -> card.removeLinkedCardSet(this));
        linkedCards.clear();
    }

    public void retainAllLinkedCards(Collection<Card> cards) {
        linkedCards.stream()
                .filter(linkedCard -> !cards.contains(linkedCard))
                .collect(Collectors.toList())
                .forEach(this::removeLinkedCard);
    }
}
