package com.okestro.omok.payload.response;


import com.okestro.omok.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDetailsResponse {

    private Long userId;

    private String email;

    private String name;

    private String image;

    @Builder
    private UserDetailsResponse(Long userId, String email, String name, String image) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.image = image;
    }

    public static UserDetailsResponse toEntity(User user) {
        return UserDetailsResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .image(user.getImage())
                .build();
    }
}
