package com.example.banquet.controller.admin;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.banquet.common.DataNotFoundException;
import com.example.banquet.common.FlashData;
import com.example.banquet.entity.Event;
import com.example.banquet.entity.EventUser;
import com.example.banquet.entity.User;
import com.example.banquet.service.EventService;
import com.example.banquet.service.EventUserService;

@Controller
@RequestMapping("/admin/eventusers")
public class EventUsersController {

    private final EventService eventService;
    private final EventUserService eventUserService;

    public EventUsersController(EventService eventService, EventUserService eventUserService) {
        this.eventService = eventService;
        this.eventUserService = eventUserService;
    }

    @PostMapping("/create/{eventId}")
    public String register(@PathVariable Integer eventId, RedirectAttributes ra,
            @AuthenticationPrincipal User user) {
        FlashData flash;
        try {
            Event event = eventService.findById(eventId);
            if (event.getMaxParticipant() <= event.getUsers().size()) {
                flash = new FlashData().danger("最大参加者数に達しています");
                ra.addFlashAttribute("flash", flash);
                return "redirect:/admin/events/view/" + eventId;
            }

            if (event.isJoin(user)) {
                flash = new FlashData().danger("既に参加しています");
                ra.addFlashAttribute("flash", flash);
                return "redirect:/admin/events/view/" + eventId;
            }

            EventUser eventUser = new EventUser();
            eventUser.setEventId(eventId);
            eventUser.setUserId(user.getId());
            eventUserService.save(eventUser);
            flash = new FlashData().success("イベントに参加しました");
        } catch (Exception e) {
            flash = new FlashData().danger("処理中にエラーが発生しました");
        }
        ra.addFlashAttribute("flash", flash);
        return "redirect:/admin/events/view/" + eventId;
    }

    @PostMapping("/delete/{eventId}")
    public String delete(@PathVariable Integer eventId, RedirectAttributes ra,
            @AuthenticationPrincipal User user) {
        FlashData flash;
        try {
            Event event = eventService.findById(eventId);
            if (!event.isJoin(user)) {
                flash = new FlashData().danger("対象イベントに参加していません");
                ra.addFlashAttribute("flash", flash);
                return "redirect:/admin/events/view/" + eventId;
            }

            EventUser eventUser = eventUserService.findByEventIdAndUserId(eventId, user.getId());
            eventUserService.deleteById(eventUser.getId());
            flash = new FlashData().success("イベントを辞退しました");
        } catch (DataNotFoundException e) {
            flash = new FlashData().danger("該当データがありません");
        } catch (Exception e) {
            flash = new FlashData().danger("処理中にエラーが発生しました");
        }
        ra.addFlashAttribute("flash", flash);
        return "redirect:/admin/events/view/" + eventId;
    }
}
