package org.ieeervce.gatekeeper.repository;

import org.ieeervce.gatekeeper.entity.RequestForm;
import org.ieeervce.gatekeeper.entity.Role;
import org.ieeervce.gatekeeper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestFormRepository extends JpaRepository<RequestForm,Long> {
    List<RequestForm> findByRequester(User user);


}
