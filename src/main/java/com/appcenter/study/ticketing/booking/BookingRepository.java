package com.appcenter.study.ticketing.booking;

import com.appcenter.study.ticketing.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
