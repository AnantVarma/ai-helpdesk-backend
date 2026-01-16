package com.anant.helpdesk.user.repository;

import com.anant.helpdesk.common.enums.TicketCategory;
import com.anant.helpdesk.common.enums.TicketStatus;
import com.anant.helpdesk.common.enums.UserRole;
import com.anant.helpdesk.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRoleAndSpecialization(UserRole role, TicketCategory specialization);

    List<User> findByRole(UserRole role);

}
