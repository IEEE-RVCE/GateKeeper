package org.ieeervce.gatekeeper.service;

import jakarta.transaction.Transactional;
import org.ieeervce.gatekeeper.exception.ItemNotFoundException;
import org.ieeervce.gatekeeper.entity.Society;
import org.ieeervce.gatekeeper.repository.SocietyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Create/Read/Update/Delete Societies
 */
@Service
public class SocietyService {
    static final String SOCIETY_ID_NOT_FOUND = "Society id not found: ";
    private final SocietyRepository societyRepository;

    public SocietyService(SocietyRepository societyRepository) {
        this.societyRepository = societyRepository;
    }

    public List<Society> list(){
        return societyRepository.findAll();
    }
    public Society add(Society society){
        return societyRepository.save(society);
    }
    public Society findOne(Integer societyId) throws ItemNotFoundException {
        return societyRepository.findById(societyId).orElseThrow(()->new ItemNotFoundException(SOCIETY_ID_NOT_FOUND + societyId));
    }
    @Transactional
    public Society edit(int societyId, Society society) throws ItemNotFoundException {
        Optional<Society> foundSocietyOptional = societyRepository.findById(societyId);
        return foundSocietyOptional.map(foundSociety->{
            society.setSocietyId(societyId);
            society.setCreatedAt(foundSociety.getCreatedAt());
            return societyRepository.save(society);
        }).orElseThrow(()->new ItemNotFoundException(SOCIETY_ID_NOT_FOUND + societyId));
    }
    public void delete(int societyId){
        societyRepository.deleteById(societyId);
    }
}
