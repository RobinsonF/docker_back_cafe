package com.cafe.cafe.controllers;

import com.cafe.cafe.models.Response;
import com.cafe.cafe.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/store")
@CrossOrigin()
public class StoreController {

    @Autowired
    private ProductsService productsService;

    @GetMapping(value = "/getProducts")
    public ResponseEntity<Response<?>> get(){
        try {
            return ResponseEntity.ok(new Response<>(true, productsService.get(), "Products"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null, "An error occurred while retrieving the products"));
        }
    }

    @GetMapping(value = "/getProducts/{id}")
    public ResponseEntity<Response<?>> getById(@PathVariable(value = "id") Integer id) {
        try {
            return ResponseEntity.ok(new Response<>(true, productsService.getById(Long.valueOf(id)), "Products"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null, "An error occurred while retrieving the product"));
        }
    }

    @GetMapping(value = "/products")
    public ResponseEntity<Response<?>> get(Pageable pageable) {
        try {
            return ResponseEntity.ok( new Response<>(true, productsService.getPageProducts(pageable), "Products"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null,  "An error occurred while retrieving the products"));
        }
    }

    @GetMapping(value = "/productsByCategory")
    public ResponseEntity<Response<?>> getProductsByCategory() {
        try {
            return ResponseEntity.ok( new Response<>(true, productsService.getProductsByCategory(), "Products"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null,  "An error occurred while retrieving the products"));
        }
    }
}
