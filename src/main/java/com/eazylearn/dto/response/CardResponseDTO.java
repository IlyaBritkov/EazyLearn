package com.eazylearn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CardResponseDTO {
    @EqualsAndHashCode.Include
    private Long id;

    @Size(max = 50)
    private String foreignWord;

    @Size(max = 50)
    private String translateWord;

    private Double proficiencyLevel;

    @Nullable
    private Long categoryId;
}