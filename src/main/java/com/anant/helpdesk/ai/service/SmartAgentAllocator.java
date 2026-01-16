package com.anant.helpdesk.ai.service;

import com.anant.helpdesk.common.enums.TicketCategory;
import com.anant.helpdesk.common.enums.TicketStatus;
import com.anant.helpdesk.common.enums.UserRole;
import com.anant.helpdesk.ticket.repository.TicketRepository;
import com.anant.helpdesk.user.entity.User;
import com.anant.helpdesk.user.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SmartAgentAllocator {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public SmartAgentAllocator(UserRepository userRepository,
                               TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    public User findBestAgent(TicketCategory category) {

        // 1️⃣ If not GENERAL → try specialization match
        if (category != TicketCategory.GENERAL) {
            var agents = userRepository.findByRoleAndSpecialization(
                    UserRole.AGENT, category
            );

            if (!agents.isEmpty()) {
                return pickLeastBusy(agents);
            }
        }

        // 2️⃣ Fallback → pick least busy agent overall
        var allAgents = userRepository.findByRole(UserRole.AGENT);

        if (allAgents.isEmpty()) return null;

        return pickLeastBusy(allAgents);
    }

    private User pickLeastBusy(List<User> agents) {
        return agents.stream()
                .min(Comparator.comparing(
                        agent -> ticketRepository.countByAssignedToAndStatus(
                                agent, TicketStatus.IN_PROGRESS
                        )
                ))
                .orElse(null);
    }
}