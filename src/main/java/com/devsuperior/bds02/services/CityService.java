package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.exceptions.DatabaseException;
import com.devsuperior.bds02.exceptions.ResourceNotFoundException;
import com.devsuperior.bds02.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAll(String sortBy) {
        Sort sort = Sort.by(sortBy);
        List<City> products = cityRepository.findAll(sort);
        return products.stream()
                .map(CityDTO::new)
                .toList();
    }

    @Transactional
    public CityDTO insert(CityDTO dto){
        City entity = new City();
        entity.setName(dto.getName());
        entity = cityRepository.save(entity);
        return new CityDTO(entity);
    }

    @Transactional
    public void delete(Long id){
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cidade não encontrada!");
        }

        try {
            cityRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

}
