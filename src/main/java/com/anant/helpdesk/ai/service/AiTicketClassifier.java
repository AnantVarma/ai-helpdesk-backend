package com.anant.helpdesk.ai.service;

import com.anant.helpdesk.common.enums.TicketCategory;
import com.anant.helpdesk.common.enums.TicketPriority;

public interface AiTicketClassifier {

    TicketCategory classifyCategory(String ticketText);

    TicketPriority classifyPriority(String ticketText);
}
