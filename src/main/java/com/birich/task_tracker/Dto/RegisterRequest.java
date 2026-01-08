package com.birich.task_tracker.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message="Username is required")
    @Size(min=4, max=20)
    private String username;

    @NotBlank(message="Password is required")
    @Size(min=6, max=50)
    private String password;

}
