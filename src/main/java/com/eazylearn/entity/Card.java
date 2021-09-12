package com.eazylearn.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.UUID;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
@Table(name = "card")
public class Card extends BaseEntity { // TODO add validation

    @Column(name = "term")
    private String term;

    @Column(name = "definition")
    private String definition;

    @Column(name = "proficiency_level")
    private Double proficiencyLevel;

    @Column(name = "created_time")
    private final Long createdTime = Calendar.getInstance().getTimeInMillis();

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "category_id")
    private UUID cardSetId; // todo fix to many to many

}
