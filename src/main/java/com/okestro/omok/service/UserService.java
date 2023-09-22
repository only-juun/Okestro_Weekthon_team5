package com.okestro.omok.service;

import com.okestro.omok.domain.Room;
import com.okestro.omok.domain.User;
import com.okestro.omok.exception.ClientException;
import com.okestro.omok.exception.ErrorCode;
import com.okestro.omok.payload.request.CreateUserRequest;
import com.okestro.omok.payload.response.UserDetailsResponse;
import com.okestro.omok.repository.RoomRepository;
import com.okestro.omok.repository.UserRepository;
import com.okestro.omok.util.EmailValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    @Transactional
    public UserDetailsResponse createUser(CreateUserRequest createUserRequest, Long userId) {
        EmailValidationUtil.validationEmail(createUserRequest.getEmail());

        Optional<User> validUser = findValidEmailUser(createUserRequest.getEmail());

        if(validUser.isPresent()) {
            User user = User.alreadyJoinUser(validUser.get());
            return UserDetailsResponse.toEntity(user);
        }

        User user = User.toEntity(createUserRequest);

        User saveUser = userRepository.save(user);

        return UserDetailsResponse.toEntity(saveUser);
    }

    private void isValidEmail(String email) {
        String pattern = "@okestro.com";

        Pattern regex = Pattern.compile(pattern);

        Matcher matcher = regex.matcher(email);

        if(!matcher.find()) {
            throw new ClientException(ErrorCode.INVALID_EMAIL);
        }
    }

    private Optional<User> findValidEmailUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void participationRoom(Long userId, Long roomId) {

        User user = findUser(userId);

        Room room = findRoom(roomId);

        List<User> rooms = findUsersWithRoom(room);

        if(isCurrentParticipant(rooms.size(), room.getLimitedAttendees())) {
            user.setRoom(room);
            return;
        }

        throw new ClientException(ErrorCode.EXCEED_PARTICIPATION_ROOM);
    }

    private boolean isCurrentParticipant(Integer current, Integer limitedAttendees) {
        return current < limitedAttendees;
    }

    private List<User> findUsersWithRoom(Room room) {
        return userRepository.findByRoom(room);
    }

    private Room findRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_ROOM));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUND_USER));
    }
}
