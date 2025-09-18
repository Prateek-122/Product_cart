package prateek.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import prateek.dto.AuthResponse;
import prateek.dto.LoginRequest;
import prateek.dto.SignupRequest;
import prateek.entity.Role;
import prateek.entity.User;
import prateek.repository.RoleRepository;
import prateek.repository.UserRepository;
import prateek.security.JwtService;
import prateek.service.AuthService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneHash(DigestUtils.md5DigestAsHex(request.getPhone().getBytes()));
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        Set<String> roleNames = request.getRoles() == null || request.getRoles().isEmpty()
                ? Set.of("CUSTOMER")
                : request.getRoles();
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName.toUpperCase())
                    .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        User saved = userRepository.save(user);
        String token = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                saved.getEmail(),
                saved.getPasswordHash(),
                saved.getRoles().stream().map(r -> (GrantedAuthority) () -> "ROLE_" + r.getName()).collect(Collectors.toSet())
        ), Map.of("roles", roles.stream().map(Role::getName).collect(Collectors.toSet())));
        return new AuthResponse(token, saved.getEmail(), roles.stream().map(Role::getName).collect(Collectors.toSet()));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        Set<String> roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(auth -> auth.replace("ROLE_", ""))
                .collect(Collectors.toSet());
        String token = jwtService.generateToken(principal, Map.of("roles", roles));
        return new AuthResponse(token, principal.getUsername(), roles);
    }
}
