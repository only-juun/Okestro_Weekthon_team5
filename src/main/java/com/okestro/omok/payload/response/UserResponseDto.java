package com.okestro.omok.payload.response;

import com.okestro.omok.domain.Room;
import com.okestro.omok.domain.User;
import com.okestro.omok.payload.dto.AttendeeInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserResponseDto {

    private List<AttendeeInfoDto> attendeeInfoDtoList;

//    public UserResponseDto(Room room) {
//        List<User> userList = room.getUsers();
//        this.attendeeInfoDtoList = userList.stream().map(UserResponseDto::new).collect(Collectors.toList());
//    }

}