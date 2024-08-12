package com.cafe.cafe.services;

import com.cafe.cafe.commons.GenericServiceAPI;
import com.cafe.cafe.entities.Products;
import com.cafe.cafe.models.CategoriesProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductsService extends GenericServiceAPI<Products, Long> {
    Iterable<Products> get();

    Optional<Products> getById(Long id);

    Products deleteProduct(Long id);

    Page<Products> getPageProducts(Pageable pageable);

    List<CategoriesProducts> getProductsByCategory();
}
