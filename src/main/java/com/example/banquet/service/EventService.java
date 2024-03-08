package com.example.banquet.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.banquet.common.DataNotFoundException;
import com.example.banquet.dao.EventDao;
import com.example.banquet.entity.Event;

@Service
public class EventService implements BaseService<Event> {

    private final EventDao eventDao;

    public EventService(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public List<Event> findAll() {
        return eventDao.findAll();
    }

    @Override
    public Event findById(Integer id) throws DataNotFoundException {
        return eventDao.findById(id);
    }

    @Override
    public void save(Event event) {
        eventDao.save(event);
    }

    @Override
    public void deleteById(Integer id) {
        eventDao.deleteById(id);
    }

    public List<Event> findByCategoryId(Integer categoryId) throws DataNotFoundException {
        return eventDao.findByCategoryId(categoryId);
    }

    public List<Event> findByUserId(Integer UserId) {
        return eventDao.findByUserId(UserId);
    }
}
