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

    @Query("select o from RoleEntity o")
    Page<RoleEntity> findAllRoles(Pageable pageable);

    //List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
    List<RoleEntity> findRoleEntitiesByRoleNameIn(List<String> roleNames);
    List<RoleEntity> findByRoleName(String name);
}
