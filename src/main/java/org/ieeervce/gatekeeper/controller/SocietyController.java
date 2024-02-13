package org.ieeervce.gatekeeper.controller;

import org.ieeervce.gatekeeper.ItemNotFoundException;
import org.ieeervce.gatekeeper.dto.SocietyDTO;
import org.ieeervce.gatekeeper.entity.Society;
import org.ieeervce.gatekeeper.service.SocietyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/society")
public class SocietyController {
    private final SocietyService societyService;
    private final ModelMapper modelMapper;

    @Autowired
    public SocietyController(SocietyService societyService, ModelMapper modelMapper) {
        this.societyService = societyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Society> list(){
        return societyService.list();
    }

    @PostMapping
    public Society add(@RequestBody SocietyDTO societyDTO) {
        Society society = modelMapper.map(societyDTO, Society.class);
        return societyService.add(society);
    }
    @GetMapping("/{societyId}")
    public Society findOne(@PathVariable int societyId) throws ItemNotFoundException {
        return societyService.findOne(societyId);
    }

    @PutMapping("/{societyId}")
    public Society edit(@PathVariable int societyId, @RequestBody SocietyDTO societyDTO) throws ItemNotFoundException {
        Society society = modelMapper.map(societyDTO, Society.class);

        return societyService.edit(societyId,society);
    }

    @DeleteMapping("/{societyId}")
    public void delete(@PathVariable int societyId){
        societyService.delete(societyId);
    }
}
