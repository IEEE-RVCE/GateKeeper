package org.ieeervce.gatekeeper.repository;

import org.ieeervce.gatekeeper.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByRoleName(String roleName);
}
