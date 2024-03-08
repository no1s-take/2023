package com.example.banquet.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.banquet.common.DataNotFoundException;
import com.example.banquet.entity.Chat;
import com.example.banquet.repository.ChatRepository;

@Repository
public class ChatDao implements BaseDao<Chat> {

    private final ChatRepository repository;

    public ChatDao(ChatRepository chatRepository) {
        this.repository = chatRepository;
    }

    @Override
    public List<Chat> findAll() {
        return repository.findAll();
    }

    @Override
    public Chat findById(Integer id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
    }

    @Override
    public void save(Chat chat) {
        repository.save(chat);
    }

    @Override
    public void deleteById(Integer id) {
        try {
            Chat chat = this.findById(id);
            repository.delete(chat);
        } catch (DataNotFoundException e) {
            System.out.println("do nothing");
        }
    }
}
