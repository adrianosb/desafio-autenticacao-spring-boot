package br.com.adrianosb.desafio_autenticacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.adrianosb.desafio_autenticacao.dto.LoginRequest;
import br.com.adrianosb.desafio_autenticacao.service.UserService;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signin(@RequestBody LoginRequest loginRequest) {
        String token = userService.signin(loginRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/foo-bar")
    public ResponseEntity<Void> foobar() {
        return ResponseEntity.noContent().build();
    }
}
