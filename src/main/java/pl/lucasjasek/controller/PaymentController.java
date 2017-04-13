package pl.lucasjasek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.lucasjasek.dto.OrderDTO;
import pl.lucasjasek.service.OrderDetailService;

import java.security.Principal;

@Controller
@SessionAttributes("orderDTO")
public class PaymentController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public PaymentController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/platnosc")
    public String payment(@ModelAttribute("orderDTO") OrderDTO orderDTO, Model model) {

        model.addAttribute("orderDTO", orderDTO);

        return "payment";
    }

    @PostMapping("/platnosc")
    public String paymentPost(@ModelAttribute("orderDTO") OrderDTO orderDTO, Principal principal) {

        if (orderDTO.isPaid()) {

            orderDetailService.createOrder(orderDTO, principal);

            return "redirect:/bilet";
        }

        return "redirect:/platnosc";
    }
}
