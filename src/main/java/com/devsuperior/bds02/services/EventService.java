package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.exceptions.ResourceNotFoundException;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional
    public EventDTO update(Long id, EventDTO dto){
        try{

            Event eventEntity = eventRepository.getReferenceById(id);
            City cityEntity = cityRepository.getReferenceById(dto.getCityId());

            eventEntity.setName(dto.getName());
            eventEntity.setDate(dto.getDate());
            eventEntity.setUrl(dto.getUrl());
            eventEntity.setCity(cityEntity);

            eventEntity = eventRepository.save(eventEntity);
            return new EventDTO(eventEntity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Evento não encontrado!");
        }
    }

}
