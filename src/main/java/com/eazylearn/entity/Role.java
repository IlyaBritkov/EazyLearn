package com.eazylearn.entity;

import com.eazylearn.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

@SuppressWarnings({"com.haulmont.jpb.LombokEqualsAndHashCodeInspection", "com.haulmont.jpb.LombokDataInspection"})
@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role {
    @Id
    @Column(name = "name")
    @Enumerated(STRING)
    @EqualsAndHashCode.Include
    private UserRole name;

    @ManyToMany(fetch = EAGER, cascade = ALL) // todo: check cascade
    @JoinTable(name = "role_authority",
            joinColumns = {@JoinColumn(name = "role_name")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name")})
    private List<Authority> authorities = new ArrayList<>();

    public Role(UserRole name) {
        this.name = name;
    }
}
