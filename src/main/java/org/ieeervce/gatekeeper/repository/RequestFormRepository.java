package org.ieeervce.gatekeeper.repository;

import org.ieeervce.gatekeeper.entity.RequestForm;
import org.ieeervce.gatekeeper.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestFormRepository extends JpaRepository<RequestForm,Long> {
    List<RequestForm> findByRequester(Sort sort,User user);
    List<RequestForm> findAll(Sort sort);
}
