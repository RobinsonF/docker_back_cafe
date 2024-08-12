package com.cafe.cafe.controllers;

import com.cafe.cafe.models.Response;
import com.cafe.cafe.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/users")
@CrossOrigin()
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/get")
    public ResponseEntity<Response<?>> get() {
        try {
            return ResponseEntity.ok( new Response<>(true, usersService.getUsers(), "User"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null,  "An error occurred while retrieving the users"));
        }
    }

    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<Response<?>> update(@PathVariable(value = "id") Integer id) {
        try {
            return ResponseEntity.ok( new Response<>(true, usersService.deleteUser(Long.valueOf(id)), "User"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, null, "User is not authenticated"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response<>(false, null, "Access is denied"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(false, null,  "An error occurred while deleted the users"));
        }
    }
}
