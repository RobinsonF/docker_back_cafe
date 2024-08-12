package com.cafe.cafe.models;

import com.cafe.cafe.entities.Categories;
import com.cafe.cafe.entities.Products;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesProducts {
    private Categories categories;
    private List<Products> products;
}
