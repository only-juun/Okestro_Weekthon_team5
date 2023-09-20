package com.okestro.omok.payload.dto;

import com.okestro.omok.domain.User;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto {

//    private Long roomId;
    private Long UserId;
    private String name;
    private String email;
    private String image;

    public UserInfoDto(User user) {
//        this.roomId = user.getRoom().getId();
        this.UserId = user.getId();
        this.name = user.getName();
        this.image = user.getImage();
        this.email = user.getEmail();
    }
}
