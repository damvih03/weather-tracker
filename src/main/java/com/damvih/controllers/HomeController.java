package com.damvih.controllers;

import com.damvih.dto.SessionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String getHomePage(HttpServletRequest request, Model model) {
        SessionDto sessionDto = (SessionDto) request.getAttribute("session");
        model.addAttribute("username", sessionDto.getUserDto().getUsername());
        return "index";
    }

}
