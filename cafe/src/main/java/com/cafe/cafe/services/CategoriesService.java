package com.cafe.cafe.services;


import com.cafe.cafe.commons.GenericServiceAPI;
import com.cafe.cafe.entities.Categories;

public interface CategoriesService extends GenericServiceAPI<Categories, Long> {

    Iterable<Categories> get();

    Categories deleteCategory(Long id);

}
