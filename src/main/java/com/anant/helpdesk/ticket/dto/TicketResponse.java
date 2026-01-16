package com.anant.helpdesk.ticket.dto;

import com.anant.helpdesk.common.enums.*;

import java.time.LocalDateTime;

public record TicketResponse(
        Long id,
        String title,
        String description,
        TicketCategory category,
        TicketPriority priority,
        TicketStatus status,
        String aiSuggestedReply,
        String createdBy,
        String assignedTo,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt
) {}
