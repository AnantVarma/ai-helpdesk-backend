package com.anant.helpdesk.ticket.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTicketRequest(
        @NotBlank String title,
        @NotBlank String description
) {}
