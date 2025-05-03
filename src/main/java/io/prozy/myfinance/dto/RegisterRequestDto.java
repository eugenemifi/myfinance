package io.prozy.myfinance.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

  @NotBlank(message = "Login is required")
  @Size(min = 4, max = 50, message = "Login must be between 4 and 50 characters")
  private String login;

  @NotBlank(message = "Password is required")
  @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
  @Pattern(regexp = "^[^_]*$", message = "Password shouldn't contain '_'")
  private String password;

  @Size(max = 100, message = "First name must be at most 100 characters")
  private String firstName;

  @Size(max = 100, message = "Last name must be at most 100 characters")
  private String lastName;

  @Email(message = "Invalid email format")
  @Size(max = 100, message = "Email must be at most 100 characters")
  private String email;
}
