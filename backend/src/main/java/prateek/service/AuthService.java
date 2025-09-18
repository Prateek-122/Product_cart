package prateek.service;

import prateek.dto.AuthResponse;
import prateek.dto.LoginRequest;
import prateek.dto.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
}
