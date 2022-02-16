package com.eazylearn.dto.request.cardset;

import com.eazylearn.enums.ProficiencyLevel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CardSetCreateRequestDTO { // todo: 6/10/2021 add validation

    private String name;

    private List<String> linkedCardsIds; // can be empty

    // todo: handle it in controller or service
    private List<CardCreateDTO> linkedCardSets; // can be empty

    @Data
    public static class CardCreateDTO {
        @Length(min = 1, max = 100)
        @NotBlank
        private String term;

        @Length(min = 1, max = 1000)
        @NotBlank
        private String definition;

        @NotNull
        private ProficiencyLevel proficiencyLevel;
    }
}
