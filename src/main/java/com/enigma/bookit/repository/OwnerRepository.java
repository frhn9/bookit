package com.enigma.bookit.repository;

import com.enigma.bookit.entity.user.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, String> {
}
