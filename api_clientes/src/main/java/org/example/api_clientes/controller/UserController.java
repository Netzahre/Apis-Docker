package org.example.api_clientes.controller;

import org.example.api_clientes.model.User;
import org.example.api_clientes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Optional;

@RestController
public class UserController {
    //http://localhost:8080/

    @Autowired
    private UserRepository userRepository;

    @GetMapping("user")
    public ResponseEntity<?> getUser(@RequestParam(name = "username") String nameInput,
                                     @RequestParam(name = "password") String passwordInputBase64) {

        Optional<User> userInput = userRepository.findByUsernameAndPassword(nameInput, passwordInputBase64);
        if (userInput.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El usuario no existe o la contraseña está errónea");
        } else {
            return ResponseEntity.ok(userInput.get());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> signIn(@RequestBody User user){
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
