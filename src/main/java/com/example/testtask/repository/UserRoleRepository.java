package com.example.testtask.repository;

import com.example.testtask.entity.UserRoleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<UserRoleEntity> findByName(String name);
}
