package org.ieeervce.gatekeeper.repository;


import org.ieeervce.gatekeeper.entity.ReviewLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewLogRepository extends JpaRepository<ReviewLog,Integer> {
    List<ReviewLog> findAllById(int Id);

    List<ReviewLog> findById(Long id, Sort sort);

}
