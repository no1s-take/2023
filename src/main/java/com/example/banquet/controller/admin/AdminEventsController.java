package com.example.banquet.controller.admin;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.banquet.entity.Event;
import com.example.banquet.entity.User;
import com.example.banquet.service.EventService;
import com.example.banquet.service.UserService;

@Controller
@RequestMapping("/admin/events")
public class AdminEventsController {

    private final EventService eventService;
    private final UserService userService;

    public AdminEventsController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/mylist")
    public String mylist(Event event, Model model, RedirectAttributes ra) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByEmail(email);
            List<Event> events = eventService.findByUserId(user.getId());
            model.addAttribute("events", events);
        } catch (Exception e) {
            FlashData flash = new FlashData().danger("処理中にエラーが発生しました");
            ra.addFlashAttribute("flash", flash);
        }
        return "/admin/events/mylist";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model, RedirectAttributes ra,
            @AuthenticationPrincipal User user) {
        try {
            Event event = eventService.findById(id);
            model.addAttribute("isJoin", event.isJoin(user));
            model.addAttribute("event", event);
        } catch (Exception e) {
            FlashData flash = new FlashData().danger("該当データがありません");
            ra.addFlashAttribute("flash", flash);
            return "redirect:/admin";
        }
        return "/admin/events/view";
    }


    @GetMapping("/create")
    public String form(Event event, Model model) {
        model.addAttribute("event", event);
        return "/admin/events/create";
    }

    @PostMapping("/create")
    public String register(@Validated Event event, BindingResult result, Model model,
            RedirectAttributes ra) {
        FlashData flash;
        try {
            if (result.hasErrors()) {
                return "/admin/events/create";
            }
            eventService.save(event);
            flash = new FlashData().success("新規作成しました");
        } catch (Exception e) {
            flash = new FlashData().danger("処理中にエラーが発生しました");
        }
        ra.addFlashAttribute("flash", flash);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra,
            @AuthenticationPrincipal User user) {
        try {
            Event event = eventService.findById(id);
            if (!user.getId().equals(event.getUser().getId())) {
                FlashData flash = new FlashData().danger("このイベントの管理者ではありません");
                ra.addFlashAttribute("flash", flash);
                return "redirect:/admin/events/mylist";
            }
            model.addAttribute("event", eventService.findById(id));
        } catch (DataNotFoundException e) {
            FlashData flash = new FlashData().danger("該当データがありません");
            ra.addFlashAttribute("flash", flash);
            return "redirect:/admin/events/mylist";
        } catch (Exception e) {
            FlashData flash = new FlashData().danger("処理中にエラーが発生しました");
            ra.addFlashAttribute("flash", flash);
            return "redirect:/admin/events/mylist";
        }
        return "/admin/events/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Validated Event event, BindingResult result,
            Model model, RedirectAttributes ra, @AuthenticationPrincipal User user) {
        FlashData flash;
        try {
            if (result.hasErrors()) {
                return "/admin/events/edit";
            }
            eventService.findById(id);
            eventService.save(event);
            flash = new FlashData().success("更新しました");
        } catch (Exception e) {
            flash = new FlashData().danger("処理中にエラーが発生しました");
        }
        ra.addFlashAttribute("flash", flash);
        return "redirect:/admin/events/mylist";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes ra,
            @AuthenticationPrincipal User user) {
        FlashData flash;
        try {
            Event event = eventService.findById(id);
            if (!user.getId().equals(event.getUser().getId())) {
                throw new Exception();
            }
            if (event.getUsers().size() > 0) {
                flash = new FlashData().danger("参加者がいるため削除できません");
                ra.addFlashAttribute("flash", flash);
                return "redirect:/admin/events/mylist";
            }
            eventService.deleteById(id);
            flash = new FlashData().success("削除が完了しました");
        } catch (DataNotFoundException e) {
            flash = new FlashData().danger("該当データがありません");
        } catch (Exception e) {
            flash = new FlashData().danger("処理中にエラーが発生しました");
        }
        ra.addFlashAttribute("flash", flash);
        return "redirect:/admin/events/mylist";
    }
}
