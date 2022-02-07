package com.eazylearn.entity;

import com.eazylearn.enums.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

@SuppressWarnings({"com.haulmont.jpb.LombokEqualsAndHashCodeInspection", "com.haulmont.jpb.LombokDataInspection"})
@Entity
@Table(name = "authority")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Authority {
    @Id
    @Column(name = "name")
    @Enumerated(STRING)
    @EqualsAndHashCode.Include
    private UserAuthority name;
}
