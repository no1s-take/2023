package com.example.orders.service;

import java.util.List;
import com.example.orders.common.DataNotFoundException;

public interface BaseService<T> {
    public List<T> findAll();

    public T findById(Integer id) throws DataNotFoundException;

    public void save(T t);

    public void deleteById(Integer id);
}
