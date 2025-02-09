package org.example.api_clientes.controller;

import org.example.api_clientes.model.User;
import org.example.api_clientes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    //http://localhost:8080/

    @Autowired
    private UserRepository userRepository;

    @GetMapping("user")
    public ResponseEntity<?> getUser(@RequestParam(name = "username") String nameInput, @RequestParam(name ="password") String passwordInput) {
        Optional<User> userInput = userRepository.findByUsernameAndPassword(nameInput, passwordInput);
        if (userInput.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe o la contraseña esta erronea");
        } else {
            return ResponseEntity.ok(userInput.get());
        }
    }

    @PostMapping("login")
    public void signIn(@RequestBody User user){
        userRepository.save(user);
    }
}
