package com.example.banquet.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.banquet.common.DataNotFoundException;
import com.example.banquet.entity.Category;
import com.example.banquet.repository.CategoryRepository;

@Repository
public class CategoryDao implements BaseDao<Category> {

    private final CategoryRepository repository;

    public CategoryDao(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category findById(Integer id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
    }

    @Override
    public void save(Category category) {
        repository.save(category);
    }

    @Override
    public void deleteById(Integer id) {
        try {
            Category category = this.findById(id);
            repository.delete(category);
        } catch (DataNotFoundException e) {
            System.out.println("do nothing");
        }
    }
}
