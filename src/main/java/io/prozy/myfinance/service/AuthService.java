package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.LoginRequestDto;
import io.prozy.myfinance.dto.LoginResponseDto;
import io.prozy.myfinance.dto.RegisterRequestDto;
import io.prozy.myfinance.dto.UserDto;
import io.prozy.myfinance.entity.UserEntity;
import io.prozy.myfinance.mappers.UserMapper;
import io.prozy.myfinance.repository.UserRepository;
import io.prozy.myfinance.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;  // Инжектируем UserMapper

    public LoginResponseDto login(LoginRequestDto request) {
        UserEntity user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new RuntimeException("Invalid login"));
        String rawPassword = request.getPassword();
        if (rawPassword == null || !passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(
                withUsername(user.getLogin())
                        .password(user.getPasswordHash())
                        .authorities("USER")
                        .build());

        return new LoginResponseDto(token, user.getUserId().toString());
    }

    @Transactional
    public UserDto register(RegisterRequestDto request) {
        Optional<UserEntity> existing = userRepository.findByLogin(request.getLogin());
        if (existing.isPresent()) {
            throw new RuntimeException("Login already taken");
        }

        UserEntity user = new UserEntity();
        user.setLogin(request.getLogin());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserRole("USER");

        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
