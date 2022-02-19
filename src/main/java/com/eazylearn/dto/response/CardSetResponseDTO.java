package com.eazylearn.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CardSetResponseDTO {
    @EqualsAndHashCode.Include
    private String id;

    private String name;

    private boolean isFavourite;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createdDateTime;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime updatedDateTime;

    private int amountOfLinkedCards; // todo: add logic in controller
}
