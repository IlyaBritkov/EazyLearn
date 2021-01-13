package com.spring.mvc.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class CardEntity {
    // TODO add validation

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "foreign_word")
    @NonNull
    private String foreignWord;

    @Column(name = "translate_word")
    @NonNull
    private String translateWord;

    @Column(name = "proficiency_level")
    @NonNull
    private double proficiencyLevel;

    @Column(name = "time_addition")
//    private final LocalDate dateAddition = LocalDate.now();s
//    private final Calendar dateAddition = Calendar.getInstance();
    private final Long timeAddition = Calendar.getInstance().getTimeInMillis();

    // TODO: add relation manyToOne, @ToString.Exclude
    @Column(name = "user_id")
    private Long userId;

    // TODO: add relation ManyToMany, @ToString.Exclude
    @Column(name = "category_id")
    private Long categoryId;


    // TODO replace by lombok annotation
    public CardEntity(@NonNull String foreignWord, @NonNull String translateWord, @NonNull double proficiencyLevel, Long userId, Long categoryId) {
        this.foreignWord = foreignWord;
        this.translateWord = translateWord;
        this.proficiencyLevel = proficiencyLevel;
        this.userId = userId;
        this.categoryId = categoryId;
    }
}
