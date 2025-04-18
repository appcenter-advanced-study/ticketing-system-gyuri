package com.appcenter.study.ticketing.booking.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateBookingRequest {
    private Long ticketId;
    private String userName;
}
