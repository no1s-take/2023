package com.example.books.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.books.common.DataNotFoundException;
import com.example.books.entity.Author;
import com.example.books.repository.AuthorRepository;

@Repository
public class AuthorDao implements BaseDao<Author> {

    @Autowired
    AuthorRepository repository;

    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Override
    public Author findById(Integer id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
    }

    @Override
    public void save(Author author) {
        repository.save(author);
    }

    @Override
    public void delete(Integer id) {
        try {
            Author author = this.findById(id);
            repository.delete(author);
        } catch (DataNotFoundException e) {
            System.out.println("do nothing");
        }
    }
}
