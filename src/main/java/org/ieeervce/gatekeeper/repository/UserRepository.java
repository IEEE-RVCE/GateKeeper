package org.ieeervce.gatekeeper.repository;

import org.ieeervce.gatekeeper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

    User  findByName(String name);
}
