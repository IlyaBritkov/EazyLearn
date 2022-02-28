package com.eazylearn.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@SuppressWarnings({"com.haulmont.jpb.LombokDataInspection", "com.haulmont.jpb.LombokEqualsAndHashCodeInspection"})
@MappedSuperclass
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BaseEntity {

    @Id
    @Column(name = "id", length = 36)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @EqualsAndHashCode.Include
    private String id;

    @Column(name = "created_datetime", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column(name = "last_update_datetime")
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;
}