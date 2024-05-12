package org.ieeervce.gatekeeper.service;

import org.ieeervce.gatekeeper.entity.ReviewLog;
import org.ieeervce.gatekeeper.repository.ReviewLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewLogService {
    private final ReviewLogRepository reviewLogRepository;

    @Autowired
    public ReviewLogService(ReviewLogRepository reviewLogRepository) {
        this.reviewLogRepository = reviewLogRepository;
    }
    public ReviewLog addReview(ReviewLog reviewLog)
    {
        return reviewLogRepository.save(reviewLog);
    }
    public List<ReviewLog> viewByFormId(int Id)
    {
        return reviewLogRepository.findAllById(Id);
    }
    public List<ReviewLog> findById(long id, Sort sort)
    {
        return reviewLogRepository.findById(id,sort);
    }



}
