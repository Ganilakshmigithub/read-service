package com.spring.read_service.repository;

import com.spring.read_service.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    List<Role> findByRoleIn(List<String> roleName);
}
