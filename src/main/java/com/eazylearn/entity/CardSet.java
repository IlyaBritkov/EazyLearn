package com.eazylearn.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
@Table(name = "cardSet")
public class CardSet extends BaseEntity { // todo: add validation

    @Column(name = "name")
    private String name;

    @Column(name = "is_favourite")
    private boolean isFavourite;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "user_id")
    private UUID userId;

}
