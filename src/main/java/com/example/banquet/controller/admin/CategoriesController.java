package com.example.banquet.controller.admin;

import java.util.List;
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
import com.example.banquet.entity.Category;
import com.example.banquet.entity.Event;
import com.example.banquet.service.CategoryService;
import com.example.banquet.service.EventService;

@Controller
@RequestMapping("/admin/categories")
public class CategoriesController {

    private final CategoryService categoryService;
    private final EventService eventService;

    public CategoriesController(CategoryService categoryService, EventService eventService) {
        this.categoryService = categoryService;
        this.eventService = eventService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "/admin/categories/list";
    }

    @GetMapping("/create")
    public String form(Category category, Model model) {
        model.addAttribute("category", category);
        return "/admin/categories/create";
    }

    @PostMapping("/create")
    public String register(@Validated Category category, BindingResult result, Model model,
            RedirectAttributes ra) {
        FlashData flash;
        try {
            if (result.hasErrors()) {
                return "/admin/categories/create";
            }
            categoryService.save(category);
            flash = new FlashData().success("新規作成しました");
        } catch (Exception e) {
            flash = new FlashData().danger("処理中にエラーが発生しました");
        }
        ra.addFlashAttribute("flash", flash);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("category", categoryService.findById(id));
        } catch (DataNotFoundException e) {
            FlashData flash = new FlashData().danger("該当データがありません");
            ra.addFlashAttribute("flash", flash);
            return "redirect:/admin/categories";
        } catch (Exception e) {
            FlashData flash = new FlashData().danger("処理中にエラーが発生しました");
            ra.addFlashAttribute("flash", flash);
            return "redirect:/admin/categories";
        }
        return "/admin/categories/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Validated Category category,
            BindingResult result, Model model, RedirectAttributes ra) {
        FlashData flash;
        try {
            if (result.hasErrors()) {
                return "/admin/categories/edit";
            }
            categoryService.findById(id);
            categoryService.save(category);
            flash = new FlashData().success("更新しました");
        } catch (Exception e) {
            flash = new FlashData().danger("処理中にエラーが発生しました");
        }
        ra.addFlashAttribute("flash", flash);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        FlashData flash;
        try {
            List<Event> events = eventService.findByCategoryId(id);
            if (events.isEmpty()) {
                categoryService.findById(id);
                categoryService.deleteById(id);
                flash = new FlashData().success("カテゴリの削除が完了しました");
            } else {
                flash = new FlashData().danger("書籍に登録されているカテゴリは削除できません");
            }
        } catch (DataNotFoundException e) {
            flash = new FlashData().danger("該当データがありません");
        } catch (Exception e) {
            flash = new FlashData().danger("処理中にエラーが発生しました");
        }
        ra.addFlashAttribute("flash", flash);
        return "redirect:/admin/categories";
    }
}
