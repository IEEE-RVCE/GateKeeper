package org.ieeervce.gatekeeper.service;

import jakarta.transaction.Transactional;
import org.ieeervce.gatekeeper.exception.ItemNotFoundException;
import org.ieeervce.gatekeeper.entity.RequestForm;
import org.ieeervce.gatekeeper.entity.User;
import org.ieeervce.gatekeeper.repository.RequestFormRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestFormService {

    static final String ITEM_NOT_FOUND = "Request Form Not Found";
    private final RequestFormRepository requestFormRepository;

    RequestFormService(RequestFormRepository requestFormRepository) {
        this.requestFormRepository = requestFormRepository;
    }

    public RequestForm findOne(Long requestFormId) throws ItemNotFoundException {
        return requestFormRepository.findById(requestFormId).orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND + requestFormId));
    }

    public List<RequestForm> findRequestsBySociety(Integer societyId)
    {
        return requestFormRepository.findByRequesterSociety(societyId);
    }
    public RequestForm add(RequestForm requestForm) {
        return requestFormRepository.save(requestForm);
    }

    public List<RequestForm> list() {
        return requestFormRepository.findAllByOrderByCreatedAtDesc();
    }

    public RequestForm save(RequestForm requestForm) {
        return requestFormRepository.save(requestForm);
    }

    public void delete(Long requestFormId) {
        requestFormRepository.deleteById(requestFormId);
    }

    @Transactional
    public RequestForm edit(Long requestFormId, RequestForm editedRequestForm) throws ItemNotFoundException {
        Optional<RequestForm> existingRequestForm = requestFormRepository.findById(requestFormId);
        return existingRequestForm.map((requestForm) -> {
            editedRequestForm.setRequestFormId(requestFormId);
            editedRequestForm.setCreatedAt(requestForm.getCreatedAt());
            return requestFormRepository.save(editedRequestForm);
        }).orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND + requestFormId));
    }

    public List<RequestForm> getRequestFormByRequester(User user) {
        return requestFormRepository.findAllByRequesterOrderByCreatedAtDesc(user);
    }

    public List<RequestForm> findFinancialRequests() {
        return requestFormRepository.findAllByIsFinanceOrderByCreatedAtDesc(true);
    }
}
