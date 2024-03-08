package com.example.banquet.controller.admin;

import org.springframework.core.Conventions;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
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
import com.example.banquet.common.FlashData;
import com.example.banquet.entity.Event;
import com.example.banquet.entity.Mail;
import com.example.banquet.entity.User;
import com.example.banquet.service.EventService;
import com.example.banquet.service.UserService;


@Controller
@RequestMapping("/admin/mail")
public class MailController {

    private final EventService eventService;
    private final MailSender mailSender;
    private final UserService userService;

    public MailController(EventService eventService, MailSender mailSender,
            UserService userService) {
        this.eventService = eventService;
        this.mailSender = mailSender;
        this.userService = userService;
    }

    @GetMapping("/{eventId}")
    public String view(@PathVariable Integer eventId, Model model, RedirectAttributes ra,
            @AuthenticationPrincipal User user) {
        if (!model.containsAttribute(BindingResult.MODEL_KEY_PREFIX + "mail")) {
            model.addAttribute("mail", new Mail());
        }
        try {
            Event event = eventService.findById(eventId);
            if (event.getUser().getId().equals(user.getId())) {
                FlashData flash = new FlashData().danger("自分が管理しているイベントです");
                ra.addFlashAttribute("flash", flash);
                return "redirect:/admin";
            }
            model.addAttribute("event", event);
        } catch (Exception e) {
            FlashData flash = new FlashData().danger("処理中にエラーが発生しました");
            ra.addFlashAttribute("flash", flash);

        }
        return "/admin/mail/view";
    }

    @PostMapping("/{eventId}")
    public String send(@PathVariable Integer eventId, @Validated Mail mail, BindingResult result,
            RedirectAttributes ra) {
        try {
            if (result.hasErrors()) {
                ra.addFlashAttribute(
                        BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(mail), result);
                return "redirect:/admin/mail/" + eventId;
            }
            Event event = eventService.findById(eventId);
            User user = userService
                    .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getUser().getEmail());
            message.setFrom(user.getEmail());
            message.setSubject(mail.getSubject());
            message.setText(mail.getText());

            mailSender.send(message);
            FlashData flash = new FlashData().success("メールを送信しました");
            ra.addFlashAttribute("flash", flash);
        } catch (Exception e) {
            FlashData flash = new FlashData().danger("処理中にエラーが発生しました");
            ra.addFlashAttribute("flash", flash);
        }

        return "redirect:/admin";
    }

}
