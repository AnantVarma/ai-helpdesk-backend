package com.anant.helpdesk.ticket.repository;

import com.anant.helpdesk.common.enums.TicketStatus;
import com.anant.helpdesk.ticket.entity.Ticket;
import com.anant.helpdesk.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreatedBy(User user);

    List<Ticket> findByAssignedTo(User agent);

    @Query("""
SELECT COUNT(t) FROM Ticket t
WHERE t.assignedTo = :agent
AND t.status <> 'RESOLVED'
""")
    int countOpenTicketsForAgent(@Param("agent") User agent);

    long countByAssignedToAndStatus(User agent, TicketStatus status);


}
