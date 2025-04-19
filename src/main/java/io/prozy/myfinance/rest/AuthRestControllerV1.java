package io.prozy.myfinance.rest;

import io.prozy.myfinance.dto.*;
import io.prozy.myfinance.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestControllerV1 {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequestDto request) {
    UserDto userDto = authService.register(request);
    return ResponseEntity.ok(userDto);
  }
}
