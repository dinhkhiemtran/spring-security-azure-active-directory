package com.example.springsecurityazure.repo;

import com.example.springsecurityazure.model.Role;
import com.example.springsecurityazure.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);
}
