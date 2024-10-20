package com.example.backend.Controller;

import com.example.backend.Authentication.AuthenticationRequest;
import com.example.backend.Authentication.AuthenticationResponse;
import com.example.backend.Authentication.AuthenticationService;
import com.example.backend.Authentication.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://eazzyfrontend.vercel.app/", maxAge = 3600, allowedHeaders = "*",allowCredentials = "true")
@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
            ){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }


}
