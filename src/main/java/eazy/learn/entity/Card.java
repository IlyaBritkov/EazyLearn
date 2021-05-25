package eazy.learn.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity
@Table(name = "card")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Card {
    // TODO add validation

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
