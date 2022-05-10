package com.eazylearn.dto.response;

import com.eazylearn.configuration.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CardResponseDTO {
    @EqualsAndHashCode.Include
    private String id;

    private String term;

    private String definition;

    @JsonProperty("isFavourite")
    private boolean isFavourite;

    private Double proficiencyLevel;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime createdDateTime;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime updatedDateTime;

    private List<String> linkedCardSetsIds;
}
