package com.example.banquet.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.banquet.service.EventService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EventService eventService;

    public AdminController(EventService eventService) {
        this.eventService = eventService;
    }

    /*
     * ダッシュボード表示
     */
    @GetMapping(path = {"/", ""})
    public String list(Model model) {
        model.addAttribute("events", eventService.findAll());
        return "admin/admin/list";
    }
}
