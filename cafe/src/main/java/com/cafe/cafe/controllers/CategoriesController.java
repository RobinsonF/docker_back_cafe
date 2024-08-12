package com.cafe.cafe.controllers;

import com.cafe.cafe.entities.Categories;
import com.cafe.cafe.models.Response;
import com.cafe.cafe.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/categories")
@CrossOrigin()
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping(value = "/get")
    public ResponseEntity<Response<?>> get() {
        try {
            return ResponseEntity.ok( new Response<>(true, categoriesService.get(), "Category"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null,  "An error occurred while retrieving the category"));
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Response<?>> save(@RequestBody Categories categories){
        try {
            return ResponseEntity.ok(new Response<>(true, categoriesService.save(categories), "Category created") );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null,  "An error occurred while created the category"));
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Response<?>> update(@RequestBody Categories categories){
        try {
            return ResponseEntity.ok(new Response<>(true, categoriesService.save(categories), "Category updated") );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null,  "An error occurred while updated the category"));
        }
    }

    @PutMapping(value = "delete/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable(value = "id") Integer id){
        try {
            return ResponseEntity.ok(new Response<>(true, categoriesService.deleteCategory(Long.valueOf(id)), "Category deleted") );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null,  "An error occurred while updated the category"));
        }
    }

}
