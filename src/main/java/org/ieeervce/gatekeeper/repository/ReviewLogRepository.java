package org.ieeervce.gatekeeper.repository;


import org.ieeervce.gatekeeper.entity.ReviewLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewLogRepository extends JpaRepository<ReviewLog,Integer> {
    List<ReviewLog> findAllByReviewLogId(int Id);

    List<ReviewLog> findByReviewLogId(Long id, Sort sort);

}
