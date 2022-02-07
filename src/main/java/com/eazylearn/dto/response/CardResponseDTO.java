package com.eazylearn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CardResponseDTO {

    @EqualsAndHashCode.Include
    private UUID id;

    private String term;

    private String definition;

    private Double proficiencyLevel;

    private Boolean isFavourite;

    private LocalDateTime createdDateTime;

    private LocalDateTime updatedDateTime;

    //todo: check whether a list is always returned or null value is possible
    @Nullable
    private List<UUID> linkedCardSetsIds;

}
