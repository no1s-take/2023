package com.example.banquet.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.banquet.common.DataNotFoundException;
import com.example.banquet.dao.CategoryDao;
import com.example.banquet.entity.Category;

@Service
public class CategoryService implements BaseService<Category> {

    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

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
    public void deleteById(Integer id) {
        categoryDao.deleteById(id);
    }
}
