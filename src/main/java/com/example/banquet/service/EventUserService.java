package com.example.banquet.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.banquet.common.DataNotFoundException;
import com.example.banquet.dao.EventUserDao;
import com.example.banquet.entity.EventUser;

@Service
public class EventUserService implements BaseService<EventUser> {

    private final EventUserDao eventUserDao;

    public EventUserService(EventUserDao eventUserDao) {
        this.eventUserDao = eventUserDao;
    }

    @Override
    public List<EventUser> findAll() {
        return eventUserDao.findAll();
    }

    @Override
    public EventUser findById(Integer id) throws DataNotFoundException {
        return eventUserDao.findById(id);
    }

    @Override
    public void save(EventUser eventUser) {
        eventUserDao.save(eventUser);
    }

    @Override
    public void deleteById(Integer id) {
        eventUserDao.deleteById(id);
    }

    public EventUser findByEventIdAndUserId(Integer eventId, Integer userId)
            throws DataNotFoundException {
        return eventUserDao.findByEventIdAndUserId(eventId, userId);
    }
}
