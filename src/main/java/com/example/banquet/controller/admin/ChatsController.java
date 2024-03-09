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
import com.example.banquet.entity.Event;
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
            Event event = eventService.findById(eventId);
            model.addAttribute("user", user);
            model.addAttribute("event", event);
            model.addAttribute("chats", chatService.findAllByOrderByCreatedAt());
            if (!event.isJoin(user)) {
                ra.addFlashAttribute("flash", new FlashData().danger("イベントに参加していません"));
                return "redirect:/admin/events/view/" + eventId;
            }
        } catch (DataNotFoundException e) {
            ra.addFlashAttribute("flash", new FlashData().danger("該当データがありません"));
            return "redirect:/admin";
        } catch (Exception e) {
            ra.addFlashAttribute("flash", new FlashData().danger("処理中にエラーが発生しました"));
            return "redirect:/admin/events/view/" + eventId;
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

            Event event = eventService.findById(chat.getEventId());
            if (!event.isJoin(user)) {
                ra.addFlashAttribute("flash", new FlashData().danger("イベントに参加していません"));
                return "redirect:/admin/events/view/" + chat.getEventId();
            }
            eventUserService.findByEventIdAndUserId(chat.getEventId(), user.getId());

            chat.setUser(user);
            chatService.save(chat);
        } catch (DataNotFoundException e) {
            ra.addFlashAttribute("flash", new FlashData().danger("該当データがありません"));
            return "redirect:/admin";
        } catch (Exception e) {
            ra.addFlashAttribute("flash", new FlashData().danger("処理中にエラーが発生しました"));
            return "redirect:/admin";
        }
        return "redirect:/admin/chats/talk/" + chat.getEventId();
    }

}
