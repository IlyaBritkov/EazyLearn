package com.eazylearn.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Calendar;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
@Table(name = "card")
public class Card extends BaseEntity{ // TODO add validation
    @Column(name = "foreign_word")
    @NonNull
    @Size(max = 50) // todo add validation variables to property files
    private String foreignWord;

    @Column(name = "translate_word")
    @NonNull
    @Size(max = 50)
    private String translateWord;

    @Column(name = "proficiency_level")
    @NonNull
    private Double proficiencyLevel;

//    @CreatedDate // // TODO: 6/3/2021  
    @Column(name = "time_addition")
    private final Long timeAddition = Calendar.getInstance().getTimeInMillis();

    //    @Column(name = "user_id")
//    @ManyToOne()
    private Long userId; // todo

    // TODO: add relation ManyToMany, @ToString.Exclude
    @Column(name = "category_id")
    private Long categoryId;




    // TODO replace by lombok annotation
    public Card(@NonNull String foreignWord, @NonNull String translateWord, @NonNull double proficiencyLevel, Long userId, Long categoryId) {
        this.foreignWord = foreignWord;
        this.translateWord = translateWord;
        this.proficiencyLevel = proficiencyLevel;
        this.userId = userId;
        this.categoryId = categoryId;
    }
}
