package br.com.nlw.events.services;

import br.com.nlw.events.dto.EventDto;
import br.com.nlw.events.entity.EventEntity;
import br.com.nlw.events.exceptions.CustomException;
import br.com.nlw.events.mappers.EventMapper;
import br.com.nlw.events.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    public EventDto addNewEvent(EventDto eventDto) {
        eventRepository.findByPrettyName(eventDto.prettyName()).ifPresent(eventEntity -> {
            throw new CustomException("Evento ja cadastrado no Sistema", HttpStatus.CONFLICT, null);
        });
        EventEntity eventEntity = eventRepository.save(eventMapper.toModel(eventDto));
        return eventMapper.toDto(eventEntity);
    }

    public List<EventDto> findAllEvent() {
        List<EventEntity> events = eventRepository.findAll();
        if (events.isEmpty()) {
            throw new CustomException("Nenhum evento encontrado!", HttpStatus.NOT_FOUND, null);
        }
        return eventMapper.listEventDto(events);
    }
}
