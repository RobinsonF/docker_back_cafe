package com.cafe.cafe.repositories;

import com.cafe.cafe.entities.Categories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends CrudRepository<Categories, Long>, QueryByExampleExecutor<Categories> {

}
