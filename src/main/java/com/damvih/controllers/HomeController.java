package com.damvih.controllers;

import com.damvih.dto.SessionDto;
import com.damvih.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SessionService sessionService;

    @GetMapping("/")
    public String getHomePage(@CookieValue(name = "SESSION_ID") String sessionId, Model model) {
        SessionDto sessionDto = sessionService.getValid(UUID.fromString(sessionId));
        model.addAttribute("username", sessionDto.getUserDto().getUsername());
        return "index";
    }

}
