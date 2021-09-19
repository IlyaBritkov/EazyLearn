package com.eazylearn.entity;

import com.eazylearn.enums.UserRole;
import com.eazylearn.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
@Table(name = "users")
public class User extends BaseEntity { // todo: add validation

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar_image_path")
    private String avatarImagePath;

    @CreatedDate
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Enumerated(STRING)
    @Column(name = "status")
    private UserStatus status;

    @Enumerated(STRING)
    @Column(name = "role")
    private UserRole role;

}
