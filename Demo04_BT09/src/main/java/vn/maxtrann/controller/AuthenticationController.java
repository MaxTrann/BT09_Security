package vn.maxtrann.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.maxtrann.entity.User;
import vn.maxtrann.model.LoginUserModel;
import vn.maxtrann.model.LoginResponse;
import vn.maxtrann.model.RegisterUserModel;
import vn.maxtrann.service.AuthenticationService;
import vn.maxtrann.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService,
                                    AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<User> register(@RequestBody RegisterUserModel registerUser) {
        User registeredUser = authenticationService.signup(registerUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping(path = "/login")
    @Transactional
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserModel loginUser) {
        User authenticatedUser = authenticationService.authenticate(loginUser);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}