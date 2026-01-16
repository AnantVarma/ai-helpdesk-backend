package com.anant.helpdesk.ticket.controller;

import com.anant.helpdesk.common.enums.TicketStatus;
import com.anant.helpdesk.ticket.dto.CreateTicketRequest;
import com.anant.helpdesk.ticket.entity.Ticket;
import com.anant.helpdesk.ticket.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // USER: create ticket
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Ticket> createTicket(
            @RequestBody CreateTicketRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                ticketService.createTicket(request, email)
        );
    }

    // USER: view own tickets
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    public ResponseEntity<List<Ticket>> getMyTickets(Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                ticketService.getMyTickets(email)
        );
    }

    // ADMIN: view all tickets
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    // ADMIN: assign ticket
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{ticketId}/assign/{agentId}")
    public ResponseEntity<String> assignTicket(
            @PathVariable Long ticketId,
            @PathVariable Long agentId) {

        ticketService.assignTicket(ticketId, agentId);
        return ResponseEntity.ok("Ticket assigned successfully");
    }

    // ADMIN: update status
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    @PutMapping("/{ticketId}/status/{status}")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long ticketId,
            @PathVariable TicketStatus status) {

        ticketService.updateStatus(ticketId, status);
        return ResponseEntity.ok("Status updated");
    }

    // AGENT: view assigned tickets
    @PreAuthorize("hasRole('AGENT')")
    @GetMapping("/assigned")
    public ResponseEntity<List<Ticket>> getAssignedTickets(Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                ticketService.getAssignedTickets(email)
        );
    }

}
