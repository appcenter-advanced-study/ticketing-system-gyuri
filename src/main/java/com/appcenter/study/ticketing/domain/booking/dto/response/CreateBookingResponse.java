package com.appcenter.study.ticketing.domain.booking.dto.response;

import com.appcenter.study.ticketing.domain.ticket.Ticket;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateBookingResponse {
    private Long bookingId;
    private String username;
    private Ticket ticket;
}
