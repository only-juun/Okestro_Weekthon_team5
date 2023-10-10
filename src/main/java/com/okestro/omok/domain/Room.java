package com.okestro.omok.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, length = 60)
    private String restaurantName;

    @Column(nullable = false)
    private String restaurantLocation;

    @Column(nullable = false, length = 30)
    private String restaurantCategory;

    @Column(nullable = false)
    private Integer limitedAttendees;

    @Column(nullable = false)
    private LocalDateTime lunchTime;

    private Double restaurantLatitude;

    private Double restaurantLongitude;

    private String locationUrl;

    @OneToMany(mappedBy = "room")
    private List<User> users = new ArrayList<>();

    private LocalDateTime deletedAt;

    @Builder
    private Room(Long id, String title, String description, String restaurantName, String restaurantLocation, String restaurantCategory, Integer limitedAttendees, LocalDateTime lunchTime, Double restaurantLatitude, Double restaurantLongitude, String locationUrl, List<User> users, LocalDateTime deletedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.restaurantCategory = restaurantCategory;
        this.limitedAttendees = limitedAttendees;
        this.lunchTime = lunchTime;
        this.restaurantLatitude = restaurantLatitude;
        this.restaurantLongitude = restaurantLongitude;
        this.locationUrl = locationUrl;
        this.users = users;
        this.deletedAt = deletedAt;
    }

    public void markAsDeleted() {
        this.deletedAt = LocalDateTime.now();
    }
}
