package com.anant.helpdesk.ticket.service;

import com.anant.helpdesk.ai.service.AiTicketClassifier;
import com.anant.helpdesk.ai.service.SmartAgentAllocator;
import com.anant.helpdesk.common.enums.TicketCategory;
import com.anant.helpdesk.common.enums.TicketPriority;
import com.anant.helpdesk.common.enums.TicketStatus;
import com.anant.helpdesk.ticket.dto.CreateTicketRequest;
import com.anant.helpdesk.ticket.entity.Ticket;
import com.anant.helpdesk.ticket.repository.TicketRepository;
import com.anant.helpdesk.user.entity.User;
import com.anant.helpdesk.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final AiTicketClassifier aiTicketClassifier;
    private final SmartAgentAllocator allocator;

    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository,
                         AiTicketClassifier aiTicketClassifier,
                         SmartAgentAllocator allocator) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.aiTicketClassifier = aiTicketClassifier;
        this.allocator = allocator;
    }




    // =========================
    // USER: CREATE TICKET (AI ENABLED)
    // =========================
    public Ticket createTicket(CreateTicketRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String combined = request.title() + " " + request.description();

        // -----------------------
        // AI Classification
        // -----------------------
        TicketCategory category;
        TicketPriority priority;

        try {
            category = aiTicketClassifier.classifyCategory(combined);
        } catch (Exception e) {
            category = TicketCategory.GENERAL;
        }

        try {
            priority = aiTicketClassifier.classifyPriority(combined);
        } catch (Exception e) {
            priority = TicketPriority.MEDIUM;
        }

        // -----------------------
        // Smart Agent Allocation
        // -----------------------
        User agent = allocator.findBestAgent(category);

        TicketStatus status = (agent == null)
                ? TicketStatus.OPEN
                : TicketStatus.IN_PROGRESS;

        // -----------------------
        // Build Ticket
        // -----------------------
        Ticket ticket = Ticket.builder()
                .title(request.title())
                .description(request.description())
                .category(category)
                .priority(priority)
                .status(status)
                .createdBy(user)
                .assignedTo(agent)
                .createdAt(LocalDateTime.now())
                .build();

        return ticketRepository.save(ticket);
    }


    // =========================
    // USER: VIEW OWN TICKETS
    // =========================
    public List<Ticket> getMyTickets(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketRepository.findByCreatedBy(user);
    }

    // =========================
    // ADMIN: VIEW ALL TICKETS
    // =========================
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // =========================
    // ADMIN: ASSIGN TICKET TO AGENT
    // =========================
    public void assignTicket(Long ticketId, Long agentId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        ticket.setAssignedTo(agent);
        ticket.setStatus(TicketStatus.IN_PROGRESS);

        ticketRepository.save(ticket);
    }

    // =========================
    // ADMIN / AGENT: UPDATE STATUS
    // =========================
    public void updateStatus(Long ticketId, TicketStatus status) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(status);

        if (status == TicketStatus.RESOLVED) {
            ticket.setResolvedAt(LocalDateTime.now());
        }

        ticketRepository.save(ticket);
    }

    // =========================
    // AGENT: VIEW ASSIGNED TICKETS
    // =========================
    public List<Ticket> getAssignedTickets(String email) {

        User agent = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        return ticketRepository.findByAssignedTo(agent);
    }
}
