package com.example.books.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.books.common.DataNotFoundException;
import com.example.books.dao.BaseDao;
import com.example.books.entity.Author;

@Service
public class AuthorService implements BaseService<Author> {
    @Autowired
    private BaseDao<Author> authorDao;

    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }

    @Override
    public Author findById(Integer id) throws DataNotFoundException {
        return authorDao.findById(id);
    }

    @Override
    public void save(Author author) {
        authorDao.save(author);
    }

    @Override
    public void delete(Integer id) {
        authorDao.delete(id);
    }
}
