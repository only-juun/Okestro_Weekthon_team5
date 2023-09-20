package com.okestro.omok.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 30)
    private String name;

    @Column(length = 30)
    private String email;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    private User(Long id, String name, String email, String image, Room room) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
        this.room = room;
    }

    public void assignRoom(Room room) {
        this.room = room;
//        room.getUsers().add(this);
    }
}
