package com.cafe.cafe.controllers;

import com.cafe.cafe.entities.Products;
import com.cafe.cafe.models.Response;
import com.cafe.cafe.services.ProductsService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(value = "/admin/product")
@CrossOrigin()
public class ProductsControllers {

    @Autowired
    private ProductsService productsService;

    @GetMapping(value = "/get")
    public ResponseEntity<Response<?>> get() {
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

    @GetMapping(value = "/get/{id}")
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

    @PostMapping("/save")
    public ResponseEntity<Response<?>> save(
            @RequestParam("name") String name, @RequestParam("description") String description,
            @RequestParam("price") Integer price, @RequestParam("category") Integer category, @RequestParam("file") MultipartFile imagen
    ) {
        try {
            Products producto = new Products();
            producto.setName(name);
            producto.setDescription(description);
            producto.setPrice(Float.valueOf(price));
            producto.setCategory(Long.valueOf(category));
            producto.setStatus(1);
            if (!imagen.isEmpty()) {
                byte[] bytesImg = imagen.getBytes();
                producto.setImage_blob(bytesImg);
                return ResponseEntity.ok(new Response<>(true, productsService.save(producto), "Product created"));
            } else {
                return ResponseEntity.ok(new Response<>(true, productsService.save(producto), "Product created but without image"));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null, "An error occurred while created the product"));
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Response<?>> update(@RequestBody Products products) {
        try {
            return ResponseEntity.ok(new Response<>(true, productsService.save(products), "Product updated"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null, "An error occurred while updated the products"));
        }
    }

    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable(value = "id") Integer id) {
        try {
            return ResponseEntity.ok(new Response<>(true, productsService.deleteProduct(Long.valueOf(id)), "Product deleted"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null, "An error occurred while deleted the products"));
        }
    }

    private String generateUniqueFileName(String originalFileName, String directoryPath) {
        String fileName = originalFileName;
        Path filePath = Paths.get(directoryPath, fileName);
        int fileNumber = 1;

        while (Files.exists(filePath)) {
            String baseName = FilenameUtils.getBaseName(originalFileName);
            String extension = FilenameUtils.getExtension(originalFileName);
            fileName = baseName + fileNumber + "." + extension;
            filePath = Paths.get(directoryPath, fileName);
            fileNumber++;
        }

        return fileName;
    }

}
