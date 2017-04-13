package pl.lucasjasek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.lucasjasek.model.OrderDetail;
import pl.lucasjasek.service.OrderDetailService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserOrdersController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public UserOrdersController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/uzytkownik/zamowienia")
    public String userOrders(Model model, Principal principal) {

        List<OrderDetail> orderDetailList = orderDetailService.findOrderDetailList(principal.getName());

        model.addAttribute("orderDetailList", orderDetailList);

        return "userOrders";
    }
}
