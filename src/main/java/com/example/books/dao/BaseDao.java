package com.example.books.dao;

import java.util.List;
import com.example.books.common.DataNotFoundException;

public interface BaseDao<T> {
    public List<T> findAll();

    public T findById(Integer id) throws DataNotFoundException;

    public void save(T t);

    public void delete(Integer id);
}
