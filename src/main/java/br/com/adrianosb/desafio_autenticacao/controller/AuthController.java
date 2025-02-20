package br.com.adrianosb.desafio_autenticacao.controller;

import br.com.adrianosb.desafio_autenticacao.dto.LoginRequest;
import br.com.adrianosb.desafio_autenticacao.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signin(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(loginRequest.username());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/foo-bar")
    public ResponseEntity<Void> foobar() {
        return ResponseEntity.noContent().build();
    }
}
