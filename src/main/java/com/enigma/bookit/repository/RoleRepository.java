package com.enigma.bookit.repository;

import com.enigma.bookit.entity.user.ERole;
import com.enigma.bookit.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
