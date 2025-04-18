package com.appcenter.study.ticketing.ticket;

import com.appcenter.study.ticketing.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
