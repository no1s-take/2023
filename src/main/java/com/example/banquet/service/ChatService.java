package com.example.banquet.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.banquet.common.DataNotFoundException;
import com.example.banquet.dao.ChatDao;
import com.example.banquet.entity.Chat;

@Service
public class ChatService implements BaseService<Chat> {

    private final ChatDao chatDao;

    public ChatService(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    @Override
    public List<Chat> findAll() {
        return chatDao.findAll();
    }

    @Override
    public Chat findById(Integer id) throws DataNotFoundException {
        return chatDao.findById(id);
    }

    @Override
    public void save(Chat category) {
        chatDao.save(category);
    }

    @Override
    public void deleteById(Integer id) {
        chatDao.deleteById(id);
    }
}
