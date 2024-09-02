package com.pucmm.eict.mockupapi.repositories;

import com.pucmm.eict.mockupapi.enums.UserRole;
import com.pucmm.eict.mockupapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(UUID id);

    Collection<Object> findByRole(UserRole userRole);

    User findByUsername(String username);

    void deleteById(UUID id);
}
