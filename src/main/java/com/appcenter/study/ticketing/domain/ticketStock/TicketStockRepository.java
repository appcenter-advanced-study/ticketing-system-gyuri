package com.appcenter.study.ticketing.domain.ticketStock;

import com.appcenter.study.ticketing.domain.ticket.Ticket;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TicketStockRepository extends JpaRepository<TicketStock, Long> {
    TicketStock findByTicket(Ticket ticket);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ts FROM TicketStock ts WHERE ts.ticket.ticketId = :ticketId")
    Optional<TicketStock> findByTicketIdForUpdate(@Param("ticketId") Long ticketId);
}
