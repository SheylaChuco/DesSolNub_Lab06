package com.techcorp.securedocs.controller;

import com.techcorp.securedocs.dto.LoginRequestDTO;
import com.techcorp.securedocs.dto.LoginResponseDTO;
import com.techcorp.securedocs.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }
}
