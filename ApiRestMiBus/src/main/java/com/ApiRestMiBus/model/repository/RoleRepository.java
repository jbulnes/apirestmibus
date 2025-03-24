package com.ApiRestMiBus.model.repository;

import com.ApiRestMiBus.model.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    List<RoleEntity> findByRoleNameIn(List<String> roleNames);
    List<RoleEntity> findByRoleName(String name);
}
