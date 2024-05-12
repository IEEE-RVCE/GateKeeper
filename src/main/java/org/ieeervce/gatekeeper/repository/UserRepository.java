package org.ieeervce.gatekeeper.repository;

import org.ieeervce.gatekeeper.entity.Role;
import org.ieeervce.gatekeeper.entity.Society;
import org.ieeervce.gatekeeper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);
    User  findByName(String name);
    List<User> findByRoleAndSociety(Role role, Society society);

    List<User> findByRole(Role role);
}
