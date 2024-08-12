package com.cafe.cafe.controllers;

import com.cafe.cafe.entities.Users;
import com.cafe.cafe.models.AuthenticationReq;
import com.cafe.cafe.models.Response;
import com.cafe.cafe.services.JwtUtilService;
import com.cafe.cafe.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/public/auth")
@CrossOrigin()
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService usuarioDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody AuthenticationReq authenticationReq) {
        Map<String, Object> response = new HashMap<>();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationReq.getEmail(), authenticationReq.getPassword())
            );

            UserDetails userDetails = usuarioDetailsService.loadUserByUsername(authenticationReq.getEmail());

            String jwt = jwtUtilService.generateToken(userDetails);

            response.put("token", jwt);
            response.put("rol", userDetails.getAuthorities());

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            response.put("error", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (AuthenticationException e) {
            response.put("error", "Authentication failed");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception e) {
            response.put("error", "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users users) {
        try {
            String encodedPassword = passwordEncoder.encode(users.getPassword());
            users.setPassword(encodedPassword);
            return ResponseEntity.ok(new Response<>(true, usersService.save(users), "User registered"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while registering the user");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Users users) {
        try {
            return ResponseEntity.ok(new Response<>(true, usersService.save(users), "User registered"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while registering the user");
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<Response<?>> getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> response = new HashMap<>();
        Users users = usersService.getByEmail(userDetails.getUsername()).orElse(null);
        response.put("id", users.getId());
        response.put("username", users.getUsername());
        response.put("email", users.getEmail());
        response.put("role", users.getRole());
        response.put("status", users.getStatus());
        return ResponseEntity.ok(new Response<>(true, response, userDetails.getUsername()));
    }
}
