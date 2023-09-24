package com.okestro.omok.payload.response;


import com.okestro.omok.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.extensions.compactnotation.PackageCompactConstructor;

@NoArgsConstructor
@Getter
public class UserDetailsResponse {

    private Long userId;

    private String email;

    private String name;

    private String image;

    private String grantType;
    private String accessToken;
    private String refreshToken;

    @Builder
    private UserDetailsResponse(Long userId, String email, String name, String image, String grantType, String accessToken, String refreshToken) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.image = image;
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static UserDetailsResponse toEntity(User user, String accessToken) {
        return UserDetailsResponse.builder()
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
