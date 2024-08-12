package com.cafe.cafe.services.impl;


import com.cafe.cafe.commons.GenericServiceImpl;
import com.cafe.cafe.entities.Categories;
import com.cafe.cafe.repositories.CategoriesRepository;
import com.cafe.cafe.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriesServiceImpl extends GenericServiceImpl<Categories, Long> implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public CrudRepository<Categories, Long> getDao() {
        return categoriesRepository;
    }

    @Override
    public Iterable<Categories> get() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories deleteCategory(Long id) {
        Optional<Categories> categories = categoriesRepository.findById(id);
        if(categories.isPresent()){
            Categories categoriesUpdate = categories.get();
            categoriesUpdate.setStatus(0);
            return categoriesRepository.save(categoriesUpdate);
        }
        return null;
    }
}
