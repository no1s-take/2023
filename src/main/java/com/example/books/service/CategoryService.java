package com.example.books.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.books.common.DataNotFoundException;
import com.example.books.dao.BaseDao;
import com.example.books.entity.Category;

@Service
public class CategoryService implements BaseService<Category> {
    @Autowired
    private BaseDao<Category> categoryDao;

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(Integer id) throws DataNotFoundException {
        return categoryDao.findById(id);
    }

    @Override
    public void save(Category category) {
        categoryDao.save(category);
    }

    @Override
    public void delete(Integer id) {
        categoryDao.delete(id);
    }
}
