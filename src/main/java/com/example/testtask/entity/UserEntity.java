package com.example.testtask.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users", schema = "test_task")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "login", length = 50, nullable = false)
    String login;

    @Column(name = "password", length = 256, nullable = false)
    String password;

    @Column(name = "full_name", length = 256, nullable = false)
    String fullName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    UserRoleEntity role;

    @Column(name = "email")
    String email;

    @ManyToOne
    @JoinColumn(name = "department_id")
    DepartmentEntity department;

    @Column(name = "birth_day", nullable = false)
    LocalDate birthDay;

    @Column(name = "active", columnDefinition = "boolean default false", nullable = false)
    Boolean active = Boolean.FALSE;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "update_date", nullable = false)
    LocalDateTime updateDate;

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UserEntity user = (UserEntity) o;

        return Objects.equals(id, user.id) &&
                Objects.equals(active, user.active) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(role, user.role) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(department, user.department) &&
                Objects.equals(birthDay, user.birthDay) &&
                Objects.equals(updateDate, user.updateDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                id, login, password, fullName, role, email, phoneNumber, department, birthDay, active, updateDate
        );
    }
}
