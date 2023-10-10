package com.okestro.omok.payload.response;

import com.okestro.omok.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserTokenDetailsResponse {

    private Long userId;

    private String email;

    private String name;

    private String image;

    private String accessToken;

    @Builder
    private UserTokenDetailsResponse(Long userId, String email, String name, String image, String accessToken) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.image = image;
        this.accessToken = accessToken;
    }

    public static UserTokenDetailsResponse toEntity(User user, String accessToken) {
        return UserTokenDetailsResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .image(user.getImage())
                .accessToken(accessToken)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class UserNameResponse {

        private String name;

        @Builder
        private UserNameResponse(String name) {
            this.name = name;
        }

        public static UserNameResponse toEntity(User user) {
            return UserNameResponse.builder()
                    .name(user.getName())
                    .build();
        }
    }
}