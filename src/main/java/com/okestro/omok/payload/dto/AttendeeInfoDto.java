package com.okestro.omok.payload.dto;

import com.okestro.omok.domain.Room;
import com.okestro.omok.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AttendeeInfoDto {

    private Long roomId;
    private Integer limitedAttendees;
    private List<UserInfoDto> userList;

    public AttendeeInfoDto(Room room) {
        this.roomId = room.getId();
        this.limitedAttendees = room.getLimitedAttendees();
        List<User> userList = room.getUsers();
        this.userList = userList.stream().map(UserInfoDto::new).collect(Collectors.toList());
    }
}
