package com.example.banquet.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.banquet.common.DataNotFoundException;
import com.example.banquet.entity.EventUser;
import com.example.banquet.repository.EventUserRepository;

@Repository
public class EventUserDao implements BaseDao<EventUser> {

    private final EventUserRepository repository;

    public EventUserDao(EventUserRepository eventUserRepository) {
        this.repository = eventUserRepository;
    }

    @Override
    public List<EventUser> findAll() {
        return repository.findAll();
    }

    @Override
    public EventUser findById(Integer id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
    }

    @Override
    public void save(EventUser eventUser) {
        repository.save(eventUser);
    }

    @Override
    public void deleteById(Integer id) {
        try {
            EventUser eventUser = this.findById(id);
            repository.delete(eventUser);
        } catch (DataNotFoundException e) {
            System.out.println("do nothing");
        }
    }

    public EventUser findByEventIdAndUserId(Integer eventId, Integer userId) {
        return repository.findByEventIdAndUserId(eventId, userId);
    }
}
