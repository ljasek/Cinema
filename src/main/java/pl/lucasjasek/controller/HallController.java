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

import java.util.List;

@Controller
@SessionAttributes("orderDTO")
public class HallController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public HallController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/miejsce")
    public String showHall(@ModelAttribute("orderDTO") OrderDTO orderDTO, Model model) {

        String hall = orderDetailService.setHallName(orderDTO.getTime(), orderDTO.getDate());
        List<Integer> bookedPlaces = orderDetailService.getBookedPlaces(hall, orderDTO.getDate(), orderDTO.getTime());
        orderDTO.setHall(hall);

        model.addAttribute("orderDTO", orderDTO);
        model.addAttribute("booked", bookedPlaces);

        return "blueHall";
    }

    @PostMapping("/miejsce")
    public String showHallPost(@ModelAttribute("orderDTO") OrderDTO orderDTO, Model model) {

        if (orderDTO.getIds().size() == 0) {
            return "redirect:/miejsce";
        }

        model.addAttribute("orderDTO", orderDTO);

        return "redirect:/platnosc";
    }
}
