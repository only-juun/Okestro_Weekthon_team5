package com.okestro.omok.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUserRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String image;

}
