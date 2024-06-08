package org.ieeervce.gatekeeper.repository;

import org.ieeervce.gatekeeper.entity.RequestForm;
import org.ieeervce.gatekeeper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestFormRepository extends JpaRepository<RequestForm,Long> {
    List<RequestForm> findAllByRequesterOrderByCreatedAtDesc(User user);
<<<<<<< HEAD

=======
  
>>>>>>> 1ac339d249e91de16696dc18d79a413ee5a84730
    List<RequestForm> findAllByOrderByCreatedAtDesc();

    @Query("SELECT rf FROM RequestForm rf WHERE rf.requester.society.societyId = :societyId ORDER BY rf.createdAt DESC")
    List<RequestForm> findByRequesterSociety(@Param("societyId") Integer societyId);
}

