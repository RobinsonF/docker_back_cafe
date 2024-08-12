package com.cafe.cafe.services.impl;


import com.cafe.cafe.commons.GenericServiceImpl;
import com.cafe.cafe.entities.Categories;
import com.cafe.cafe.entities.Products;
import com.cafe.cafe.models.CategoriesProducts;
import com.cafe.cafe.repositories.CategoriesRepository;
import com.cafe.cafe.repositories.ProductsRepository;
import com.cafe.cafe.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductsServiceImpl extends GenericServiceImpl<Products, Long> implements ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public CrudRepository<Products, Long> getDao() {
        return productsRepository;
    }

    @Override
    public Iterable<Products> get() {
        return productsRepository.findAll();
    }

    @Override
    public Optional<Products> getById(Long id) {
        return productsRepository.findById(id);
    }

    @Override
    public Products deleteProduct(Long id) {
        Optional<Products> products = productsRepository.findById(id);
        if(products.isPresent()){
            Products productsUpdate = products.get();
            productsUpdate.setStatus(0);
            return productsRepository.save(productsUpdate);
        }
        return null;
    }

    @Override
    public Page<Products> getPageProducts(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }

    @Override
    public List<CategoriesProducts> getProductsByCategory() {
        List<CategoriesProducts> response = new ArrayList<>();
        Iterable<Categories> categories = categoriesRepository.findAll();
        for (Categories categories1: categories){
            List<Products> products = productsRepository.getByCategory(categories1.getId());
            response.add(new CategoriesProducts(categories1, products));
        }
        return response;
    }
}
