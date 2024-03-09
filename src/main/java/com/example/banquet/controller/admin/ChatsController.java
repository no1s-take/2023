package com.example.banquet.controller.admin;

import org.springframework.core.Conventions;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.banquet.common.DataNotFoundException;
import com.example.banquet.common.FlashData;
import com.example.banquet.entity.Chat;
import com.example.banquet.entity.User;
import com.example.banquet.service.ChatService;
import com.example.banquet.service.EventService;
import com.example.banquet.service.EventUserService;


@Controller
@RequestMapping("/admin/chats/")
public class ChatsController {

    private final ChatService chatService;
    private final EventService eventService;
    private final EventUserService eventUserService;

    public ChatsController(ChatService chatService, EventService eventService,
            EventUserService eventUserService) {
        this.chatService = chatService;
        this.eventService = eventService;
        this.eventUserService = eventUserService;
    }

    @GetMapping("/talk/{eventId}")
    public String talk(@PathVariable Integer eventId, Model model, RedirectAttributes ra,
            @AuthenticationPrincipal User user) {
        if (!model.containsAttribute(BindingResult.MODEL_KEY_PREFIX + "chat")) {
            model.addAttribute("chat", new Chat());
        }

        try {
            model.addAttribute("user", user);
            model.addAttribute("event", eventService.findById(eventId));
            model.addAttribute("chats", chatService.findAllByOrderByCreatedAt());
        } catch (Exception e) {
            FlashData flash = new FlashData().danger("処理中にエラーが発生しました");
            ra.addFlashAttribute("flash", flash);
        }

        return "/admin/chats/talk/talk";
    }

    @PostMapping(value = "/create")
    public String register(@Validated Chat chat, BindingResult result, Model model,
            RedirectAttributes ra, @AuthenticationPrincipal User user) {
        try {
            if (result.hasErrors()) {
                ra.addFlashAttribute(
                        BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(chat), result);
                return "redirect:/admin/chats/talk/" + chat.getEventId();
            }

            eventService.findById(chat.getEventId());
            eventUserService.findByEventIdAndUserId(chat.getEventId(), user.getId());

            chat.setUser(user);
            chatService.save(chat);
        } catch (DataNotFoundException e) {
            FlashData flash = new FlashData().danger("該当データがありません");
            ra.addFlashAttribute("flash", flash);
            return "redirect:/admin";
        } catch (Exception e) {
            FlashData flash = new FlashData().danger("処理中にエラーが発生しましたあ");
            ra.addFlashAttribute("flash", flash);
        }
        return "redirect:/admin/chats/talk/" + chat.getEventId();
    }

}
