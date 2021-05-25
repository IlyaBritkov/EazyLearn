package eazy.learn.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
public class CardDto {
    @NonNull
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @Size(max = 50)
    private String foreignWord;

    @NonNull
    @Size(max = 50)
    private String translateWord;

    @NonNull
    private Double proficiencyLevel;

    private Long userId;

    private Long categoryId;
}
