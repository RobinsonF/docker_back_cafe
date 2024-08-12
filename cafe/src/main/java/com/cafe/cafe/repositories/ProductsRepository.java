package com.cafe.cafe.repositories;

import com.cafe.cafe.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends CrudRepository<Products, Long>, QueryByExampleExecutor<Products> {

    Page<Products> findAll(Pageable pageable);
    List<Products> getByCategory(Long id);
}
