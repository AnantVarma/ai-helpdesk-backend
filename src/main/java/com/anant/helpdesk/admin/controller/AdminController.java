package com.anant.helpdesk.admin.controller;

import com.anant.helpdesk.admin.dto.CreateAgentRequest;
import com.anant.helpdesk.admin.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-agent")
    public ResponseEntity<String> createAgent(
            @RequestBody CreateAgentRequest request) {

        System.out.println("COn");
        adminService.createAgent(request);
        return ResponseEntity.ok("Agent created successfully");
    }
}
