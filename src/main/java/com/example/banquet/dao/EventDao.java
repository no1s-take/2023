package com.example.banquet.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.banquet.common.DataNotFoundException;
import com.example.banquet.entity.Event;
import com.example.banquet.repository.EventRepository;

@Repository
public class EventDao implements BaseDao<Event> {

    private final EventRepository repository;

    public EventDao(EventRepository eventRepository) {
        this.repository = eventRepository;
    }

    @Override
    public List<Event> findAll() {
        return repository.findAll();
    }

    @Override
    public Event findById(Integer id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
    }

    @Override
    public void save(Event event) {
        repository.save(event);
    }

    @Override
    public void deleteById(Integer id) {
        try {
            Event event = this.findById(id);
            repository.delete(event);
        } catch (DataNotFoundException e) {
            System.out.println("do nothing");
        }
    }

    public List<Event> findByCategoryId(Integer categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    public List<Event> findByUserId(Integer UserId) {
        return repository.findByUserId(UserId);
    }
}
