package com.eazylearn.dto.response;

import com.eazylearn.configuration.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CardSetResponseDTO {
    @EqualsAndHashCode.Include
    private String id;

    private String name;

    @JsonProperty("isFavourite")
    private boolean isFavourite;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime createdDateTime;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime updatedDateTime;

    private List<String> linkedCardsIds;
}
