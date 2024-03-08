package com.example.orders.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.orders.common.FlashData;
import com.example.orders.entity.Order;
import com.example.orders.entity.OrderDetail;
import com.example.orders.service.OrderDetailService;
import com.example.orders.service.OrderService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/admin/orderdetails")
public class OrderDetailsController {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    /*
     * 新規作成画面表示
     */
    @GetMapping(value = "/create/{order_id}")
    public String form(@PathVariable Integer order_id, OrderDetail orderDetail, Model model,
            RedirectAttributes ra) {
        try {
            orderService.findById(order_id);
            model.addAttribute("order_id", order_id);
            model.addAttribute("orderDetail", orderDetail);
        } catch (Exception e) {
            FlashData flash = new FlashData().danger("該当データがありません");
            ra.addFlashAttribute("flash", flash);
            return "redirect:/admin/orders";

        }
        return "admin/orderdetails/create";
    }

    /*
     * 新規登録
     */
    @PostMapping(value = "/create/{order_id}")
    public String register(@PathVariable Integer order_id, @Valid OrderDetail orderDetail,
            BindingResult result, Model model, RedirectAttributes ra) {
        FlashData flash;
        try {
            if (result.hasErrors()) {
                return "admin/orderdetails/create";
            }
            Order order = orderService.findById(order_id);
            orderDetail.setOrder(order);
            orderDetailService.save(orderDetail);
            flash = new FlashData().success("新規作成しました");
        } catch (Exception e) {
            flash = new FlashData().danger("処理中にエラーが発生しました");
        }
        ra.addFlashAttribute("flash", flash);

        return "redirect:/admin/orders/view/" + order_id;
    }

    /*
     * 編集画面表示
     */
    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        try {
            // 存在確認
            OrderDetail orderDetail = orderDetailService.findById(id);
            model.addAttribute("orderDetail", orderDetail);
        } catch (Exception e) {
            FlashData flash = new FlashData().danger("該当データがありません");
            ra.addFlashAttribute("flash", flash);
            return "redirect:/admin/orders";
        }
        return "admin/orderdetails/edit";
    }

    /*
     * 更新
     */
    @PostMapping(value = "/edit/{id}")
    public String update(@PathVariable Integer id, @Valid OrderDetail orderDetail,
            BindingResult result, Model model, RedirectAttributes ra) {
        FlashData flash;

        try {
            if (result.hasErrors()) {
                return "admin/orderdetails/edit";
            }
            OrderDetail detail = orderDetailService.findById(id);
            // 更新
            orderDetail.setOrder(detail.getOrder());
            orderDetailService.save(orderDetail);
            flash = new FlashData().success("更新しました");
        } catch (Exception e) {
            flash = new FlashData().danger("該当データがありません");
        }
        ra.addFlashAttribute("flash", flash);
        return "redirect:/admin/orders/view/" + orderDetail.getOrder().getId();
    }
}
