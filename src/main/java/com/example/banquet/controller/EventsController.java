package com.example.banquet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.banquet.common.FlashData;
import com.example.banquet.service.EventService;

@Controller
public class EventsController {

    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        model.addAttribute("events", eventService.findAll());
        return "/events/list";
    }

    @GetMapping(value = "/events/view/{id}")
    public String view(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("event", eventService.findById(id));
        } catch (Exception e) {
            FlashData flash = new FlashData().danger("該当データがありません");
            ra.addFlashAttribute("flash", flash);
            return "redirect:/events/list";
        }
        return "/events/view";
    }

}
