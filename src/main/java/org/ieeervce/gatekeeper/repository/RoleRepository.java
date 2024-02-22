package org.ieeervce.gatekeeper.repository;

import org.ieeervce.gatekeeper.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
