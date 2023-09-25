package com.okestro.omok.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomSaveRequestDto {

    @NotBlank(message = "제목을 필수로 입력해주세요")
    private String title;

    @Size(min = 10, message = "내용을 10자 이상 입력해주세요")
    private String description;

    @NotBlank(message = "음식점을 필수로 선택해주세요")
    private String restaurantName;

    @NotBlank
    private String restaurantLocation;

    @NotBlank
    private String locationUrl;

    @NotBlank
    private String restaurantCategory;

    @NotNull
    private Double restaurantLatitude;

    @NotNull
    private Double restaurantLongitude;

    private LocalDateTime lunchTime;

    @Min(value = 2, message = "참가 인원은 2명 이상이어야 합니다")
    private Integer limitedAttendees;

    @NotNull
    private Long userId;
}