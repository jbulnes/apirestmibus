package com.ApiRestMiBus.model.repository;

import com.ApiRestMiBus.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    boolean existsByUsername(String username);

    Optional<UserEntity> findUserEntityByUsername(String username);

}
