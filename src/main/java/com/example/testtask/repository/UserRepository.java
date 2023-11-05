package com.example.testtask.repository;

import com.example.testtask.entity.DepartmentEntity;
import com.example.testtask.entity.UserEntity;
import com.example.testtask.entity.UserRoleEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("""
            select u from UserEntity u
            where u.department = :department
            and u.updateDate < :thirtyDay
            """)
    List<UserEntity> findAllByDepartmentAndUpdateDate(
            DepartmentEntity department, LocalDateTime thirtyDay
    );

    @Query("""
            SELECT u FROM UserEntity u
            WHERE u.role.name = 'ADMIN'
            AND u.active = true
            AND u.department IN (
                SELECT d FROM DepartmentEntity d
                WHERE d.active = true
                AND (SELECT COUNT(u2) FROM UserEntity u2 WHERE u2.department = d) > 10
            )
            """)
    List<UserEntity> findAllCertainUsers(UserRoleEntity userRole, boolean active, PageRequest pageRequest);
}
