package com.okestro.omok.service;

import com.okestro.omok.domain.Room;
import com.okestro.omok.domain.User;
import com.okestro.omok.exception.ClientException;
import com.okestro.omok.exception.ErrorCode;
import com.okestro.omok.repository.RoomRepository;
import com.okestro.omok.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private UserService userService;

    private User getUser() {
        return User.builder()
                .id(1L)
                .name("김경민")
                .email("km.kim@okestro.com")
                .image("image1")
                .room(null)
                .build();
    }

    private Room getRoom() {
        return Room.builder()
                .id(1L).title("와플").description("와플 드실분").restaurantName("와플 디저트")
                .restaurantLocation("서울 영등포구 의사당대로 108 아일렉스 지하1층 비06호")
                .restaurantCategory("디저트카페").limitedAttendees(4).lunchTime(LocalDateTime.now())
                .deletedAt(null)
                .build();
    }

    private final Long userId = 1L;
    private final Long roomId = 1L;

    @Test
    @DisplayName("유저 방 참여 성공 테스트")
    public void participationRoom_Success() {
        User user = getUser();
        Room room = getRoom();

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        when(roomRepository.findByIdAndDeletedAtIsNull(roomId)).thenReturn(Optional.ofNullable(room));
        when(userRepository.findByRoom(room)).thenReturn(List.of(Objects.requireNonNull(user)));
        userService.participationRoom(userId,roomId);

        verify(userRepository, Mockito.times(1)).findById(userId);
        verify(roomRepository, Mockito.times(1)).findByIdAndDeletedAtIsNull(roomId);
        verify(userRepository, Mockito.times(1)).findByRoom(room);
        assertEquals(user.getRoom(),room);
    }

    @Test
    @DisplayName("이미 방에 참가한 유저 실패 테스트")
    public void participationRoom_AlreadyRoom_Fail(){
        User user = getUser();
        Room room = getRoom();
        user.assignRoom(room);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Assertions.assertThrows(ClientException.class,() -> {
            userService.participationRoom(userId,roomId);
        }, ErrorCode.ALREADY_PARTICIPATION_ROOM.getMessage());

        verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    @DisplayName("방 인원이 초과 됐을때 실패 테스트")
    public void participationRoom_ExceedRoom_Fail(){
        User user = getUser();
        List<User> participationRoomUsers = IntStream.range(0, 4)
                .mapToObj(i -> getUser()).toList();

        Room room = getRoom();

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        when(roomRepository.findByIdAndDeletedAtIsNull(roomId)).thenReturn(Optional.ofNullable(room));
        when(userRepository.findByRoom(room)).thenReturn(participationRoomUsers);

        Assertions.assertThrows(ClientException.class,() -> {
            userService.participationRoom(userId,roomId);
        }, ErrorCode.EXCEED_PARTICIPATION_ROOM.getMessage());

        verify(roomRepository, Mockito.times(1)).findByIdAndDeletedAtIsNull(roomId);
        verify(userRepository, Mockito.times(1)).findByRoom(room);
        verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    @DisplayName("방 인원이 한명 남았을 때 방 삭제")
    public void participationRoom_DeletedRoom() {
        User user = getUser();
        List<User> participationRoomUsers = IntStream.range(0, 3)
                .mapToObj(i -> getUser()).toList();

        List<User> exitRoomUsers = IntStream.range(0, 1)
                .mapToObj(i -> getUser()).toList();

        Room room = Room.builder()
                .id(2L).title("와플").description("와플 드실분").restaurantName("와플 디저트")
                .restaurantLocation("서울 영등포구 의사당대로 108 아일렉스 지하1층 비06호")
                .restaurantCategory("디저트카페").limitedAttendees(4).lunchTime(LocalDateTime.now())
                .deletedAt(null)
                .build();

        user.assignRoom(room);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roomRepository.findByIdAndDeletedAtIsNull(roomId)).thenReturn(Optional.ofNullable(room));
        when(userRepository.findByRoom(room)).thenReturn(participationRoomUsers);
        when(userRepository.findByRoom(room)).thenReturn(exitRoomUsers);

        userService.participationRoom(userId,roomId);


        verify(roomRepository, Mockito.times(1)).findByIdAndDeletedAtIsNull(roomId);
        verify(userRepository, Mockito.times(2)).findByRoom(room);
        verify(userRepository, Mockito.times(1)).findById(userId);

        assertNotNull(room.getDeletedAt());
    }
}